package br.com.lupus.controllers;

import java.util.HashMap;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.models.Item;
import br.com.lupus.models.TipoItem;
import br.com.lupus.models.TipoItemTag;
import br.com.lupus.models.Usuario;
import br.com.lupus.services.TipoItemService;
import br.com.lupus.utils.BindingResultUtils;

/**
 * Controller com os end-points relacionados ao CRUD dos tipos dos itens no sistema
 * 
 * @author Mateus A.S
 */
@RestController
@RequestMapping("/api/v1/tipoitem")
public class TipoItemController {

	@Autowired
	private TipoItemService tipoItemService;

	/**
	 * End-point de URL /api/v1/tipoitem - Retorna ao cliente que fez a requisição
	 * um array de objetos JSON representando os tipo-itens cadastrados no sistema
	 * 
	 * @return ResponseEntity populado com os tipo-itens cadastrados no sistema
	 */
	@GetMapping
	private ResponseEntity<Object> listatTipos() {
		try {
			TipoItem.setParametros(new TipoItem(), "id", "nome");
			return ResponseEntity.ok(tipoItemService.buscarTipos());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/tipoitem/{id do tipo-item procurado} - Retorna ao
	 * cliente que fez a requisição um objeto JSON representando o tipo-item
	 * solicitado
	 * 
	 * @param id
	 *            id presente na URL para procura do tipo-item
	 * @return ResponseEntity populado com o tipo-item solicitado com status 200
	 *         (OK), 404 (NOT FOUND) ou 500 (INTERNAL SERVER ERROR)
	 */
	@GetMapping("/{id}")
	private ResponseEntity<Object> buscarTipo(@PathVariable Long id) {
		try {
			TipoItem.setParametros(new TipoItem(), "id", "nome", "cadastrante", "tagsAnexadas", "itensAnexados");
			Usuario.setParametros(new Usuario(), "id", "nome", "email", "permissao", "ativo");
			TipoItemTag.setParametros(new TipoItemTag(), "id", "cabecalho", "corpo", "tipo");
			Item.setParametros(new Item(), "id");
			return ResponseEntity.ok(tipoItemService.buscaTipo(id));
		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/tipoitem - Recebe um objeto JSON no corpo da
	 * requisição representando um tipo-item a ser cadastrado, o end-point valida ele
	 * e tenta persistir, se o processo ocorrer com sucesso retorna um status 204
	 * 
	 * @param tipoitem
	 *            tipo-item a ser cadastrado
	 * @param brTipo
	 *            objeto populado com os possíveis erros de validação
	 * @return ResponseEntity populado com status 202 (OK - NO CONTENT), 422
	 *         (UNPROCESSABLE ENTITY) ou 500 (INTERNAL SERVER ERROR)
	 */
	@PostMapping
	private ResponseEntity<Object> cadastrarTipo(@Valid @RequestBody TipoItem tipoItem, BindingResult brTipo) {
		try {
			tipoItemService.persistirTipo(tipoItem, brTipo);
			return ResponseEntity.noContent().build();
		} catch (UnprocessableEntityException e) {
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brTipo));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * End-point de URL /api/v1/tipoitem/{id do tipo-item a ser editado} - Recebe no
	 * corpo da requisição um objeto JSON representando um tipo-item a ser editado, o
	 * end-point valida ele e tenta editar no id informado na url da requisição, se
	 * o processo ocorrer com sucesso retorna o tipo-item editado
	 * 
	 * @param id
	 *            id do tipo-item a ser editado
	 * @param tipoItem
	 *            objeto tipo-item com os valores a serem alterados
	 * @param brTipo
	 *            objeto populado com os possíveis erros de validação
	 * @return ResponseEntity populado com o tipo-item editado com status 202 (OK -
	 *         NO CONTENT), 404 (NOT FOUND), 422 (UNPROCESSABLE ENTITY) ou 500
	 *         (INTERNAL SERVER ERROR)
	 */
	@PutMapping("/{id}")
	private ResponseEntity<Object> editarTipo(@PathVariable Long id, @Valid @RequestBody TipoItem tipoItem, BindingResult brTipo) {
		try {
			tipoItem.setId(id);
			return ResponseEntity.ok(tipoItemService.editarTipo(tipoItem, brTipo));
		} catch (UnprocessableEntityException e) {
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brTipo));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * End-point de URL /api/v1/tipoitem/{id do tipo-item a ser deletado} - Recebe na
	 * URL o id de um tipo-item a ser excluido, retornando no ResponseEntity o status
	 * 202 se tudo ocorrer bem, 404 se o tipo-item referente não existir, 422 se
	 * existirem itens apontando para este tipo-item e 500 para outros possíveis
	 * erros não tratados
	 * 
	 * @param id
	 *            id do tipo-item a ser excluido
	 * @return ResponseEntity com status 202 (OK - NO CONTENT), 404 (NOT FOUND), 422
	 *         (UNPROCESSABLE ENTITY) ou 500 (INTERNAL SERVER ERROR)
	 */
	@DeleteMapping("/{id}")
	private ResponseEntity<Object> deletarTipo(@PathVariable Long id){
		try {
			tipoItemService.deletarTipo(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();
		} catch (UnprocessableEntityException e) {
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("itens", "Edite os itens referenciados a esse tipo-item antes de exclui-lo");
			return ResponseEntity.unprocessableEntity().body(hashMap);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
