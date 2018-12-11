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
import br.com.lupus.models.Ambiente;
import br.com.lupus.models.Item;
import br.com.lupus.models.TipoItem;
import br.com.lupus.models.Usuario;
import br.com.lupus.services.AmbienteService;
import br.com.lupus.utils.BindingResultUtils;

/**
 * Controller com os end-points relacionados ao CRUD de ambientes no sistema
 * 
 * @author Mateus A.S
 */
@RestController
@RequestMapping("/api/v1/ambiente")
public class AmbienteController {

	@Autowired
	public AmbienteService ambienteService;

	/**
	 * End-point de URL /api/v1/ambiente - Retorna ao cliente que fez a requisição
	 * um array de objetos JSON representando os ambientes cadastrados no sistema
	 * 
	 * @return ResponseEntity populado com os ambientes cadastrados no sistema
	 */
	@GetMapping
	public ResponseEntity<Object> listarAmbientes() {
		try {
			// 200 - OK
			Ambiente.setParametros(new Ambiente(), "id", "descricao");
			return ResponseEntity.ok(ambienteService.buscarAmbientes());
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/ambiente/{id do ambiente procurado} - Retorna ao
	 * cliente que fez a requisição um objeto JSON representando o ambiente
	 * solicitado
	 * 
	 * @param id
	 *            id presente na URL para procura do ambiente
	 * @return ResponseEntity populado com o ambiente solicitado com status 200
	 *         (OK), 404 (NOT FOUND) ou 500 (INTERNAL SERVER ERROR)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarAmbiente(@PathVariable Long id) {
		try {
			// 200 - OK
			Ambiente.setParametros(new Ambiente(), "id", "descricao", "cadastrante", "itens");
			Usuario.setParametros(new Usuario(), "id", "nome", "ativo");
			Item.setParametros(new Item(), "id", "tipo");
			TipoItem.setParametros(new TipoItem(), "nome");
			return ResponseEntity.ok(ambienteService.buscarAmbiente(id));
		} catch (EntityNotFound e) {
			// 404 - NOT FOUND
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/ambiente - Recebe um objeto JSON no corpo da
	 * requisição representando um ambiente a ser cadastrado, o end-point valida ele
	 * e tenta persistir, se o processo ocorrer com sucesso retorna um status 204
	 * 
	 * @param ambiente
	 *            ambiente a ser cadastrado
	 * @param brAmbiente
	 *            objeto populado com os possíveis erros de validação
	 * @return ResponseEntity populado com status 202 (OK - NO CONTENT), 422
	 *         (UNPROCESSABLE ENTITY) ou 500 (INTERNAL SERVER ERROR)
	 */
	@PostMapping
	public ResponseEntity<Object> cadastrarAmbiente(@Valid @RequestBody Ambiente ambiente, BindingResult brAmbiente) {
		try {
			// 202 - OK / NO CONTENT
			ambienteService.persistirAmbiente(ambiente, brAmbiente);
			return ResponseEntity.noContent().build();
		} catch (UnprocessableEntityException e) {
			// 422 - UNPROCESSABLE ENTITY
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brAmbiente));
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/ambiente/{id do ambiente a ser editado} - Recebe no
	 * corpo da requisição um objeto JSON representando um ambiente a ser editado, o
	 * end-point valida ele e tenta editar no id informado na url da requisição, se
	 * o processo ocorrer com sucesso retorna o ambiente editado
	 * 
	 * @param id
	 *            id do ambiente a ser editado
	 * @param ambiente
	 *            objeto ambiente com os valores a serem alterados
	 * @param brAmbiente
	 *            objeto populado com os possíveis erros de validação
	 * 
	 * @return ResponseEntity populado com o ambiente editado com status 202 (OK -
	 *         NO CONTENT), 404 (NOT FOUND), 422 (UNPROCESSABLE ENTITY) ou 500
	 *         (INTERNAL SERVER ERROR)
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> editarAmbiente(@PathVariable Long id, @Valid @RequestBody Ambiente ambiente,
			BindingResult brAmbiente) {
		try {
			// 200 - OK
			ambiente.setId(id);
			Ambiente.setParametros(new Ambiente(), "id", "descricao", "cadastrante");
			Usuario.setParametros(new Usuario(), "id", "nome", "email", "permissao", "ativo");
			return ResponseEntity.ok(ambienteService.atualizar(ambiente, brAmbiente));
		} catch (EntityNotFound e) {
			// 404 - NOT FOUND
			return ResponseEntity.notFound().build();
		} catch (UnprocessableEntityException e) {
			// 422 - UNPROCESSABLE ENTITY
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brAmbiente));
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/ambiente/{id do ambiente a ser deletado} - Recebe na
	 * URL o id de um ambiente a ser excluido, retornando no ResponseEntity o status
	 * 202 se tudo ocorrer bem, 404 se o ambiente referente não existir, 422 se
	 * existirem itens apontando para este ambiente e 500 para outros possíveis
	 * erros não tratados
	 * 
	 * @param id
	 *            id do ambiente a ser excluido
	 * @return ResponseEntity com status 202 (OK - NO CONTENT), 404 (NOT FOUND), 422
	 *         (UNPROCESSABLE ENTITY) ou 500 (INTERNAL SERVER ERROR)
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarAmbiente(@PathVariable Long id) {
		try {
			// 202 - NO CONTENT
			ambienteService.deletarAmbiente(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFound e) {
			// 404 - NOT FOUND
			return ResponseEntity.notFound().build();
		} catch (UnprocessableEntityException e) {
			// 422 - UNPROCESSABLE ENTITY
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("itens", "Mova os itens referenciados a esse ambiente antes de exclui-lo");
			return ResponseEntity.unprocessableEntity().body(hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
