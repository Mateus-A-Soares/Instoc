package br.com.lupus.controllers;

import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lupus.exceptions.EntityNotFoundException;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.models.Usuario;
import br.com.lupus.services.UsuarioService;
import br.com.lupus.utils.BindingResultUtils;

/**
 * Controller com os end-points relacionados ao CRUD de usuários no sistema
 * Necessita de permissão de administrador para acessar qualquer um dos
 * end-points
 * 
 * @author Mateus A.S
 */
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * End-point de URL /api/v1/usuario - Retorna ao cliente que fez a requisição um
	 * array de objetos JSON representando os usuários cadastrados no sistema
	 * 
	 * @return ResponseEntity populado com os usuários cadastrados no sistema
	 */
	@GetMapping("")
	public ResponseEntity<Object> listarUsuarios() {
		try {
			// 200 - OK
			Usuario.setParametros(new Usuario(), "id", "nome", "email", "dataNascimento", "permissao", "ativo");
			return ResponseEntity.ok(usuarioService.buscarTodos());
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/usuario/{id do usuario procurado} - Retorna ao
	 * cliente que fez a requisição um objeto JSON representando o usuário
	 * solicitado
	 * 
	 * @param id
	 *            id presente na URL para procura do usuário
	 * @return ResponseEntity populado com o usuário solicitado
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarUsuario(@PathVariable Long id) {
		try {
			// 200 - OK
			Usuario.setParametros(new Usuario(), "id", "nome", "email", "dataNascimento", "permissao", "ativo");
			return ResponseEntity.ok(usuarioService.buscar(id));
		} catch (EntityNotFoundException e) {
			// 404 - NOT FOUND
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/usuario - Recebe no corpo da requisição um objeto
	 * JSON representando um usuário a ser cadastrado, o end-point valida ele e
	 * tenta persistir, se o processo ocorrer com sucesso retorna um status 204
	 * 
	 * @param usuario
	 *            usuário a ser cadastrado
	 * @param brUsuario
	 *            objeto populado com os possíveis erros de validação
	 * @return ResponseEntity com status 202
	 */
	@PostMapping("")
	public ResponseEntity<Object> cadastrarUsuario(@Valid @RequestBody Usuario usuario, BindingResult brUsuario) {
		try {
			// 202 - OK / NO CONTENT
			System.out.println(usuario.toString());
			usuarioService.persistir(usuario, brUsuario);
			return ResponseEntity.noContent().build();
		} catch (UnprocessableEntityException e) {
			// 422 - UNPROCESSABLE ENTITY
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brUsuario));
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/usuario/{id do usuario a ser editado} - Recebe no
	 * corpo da requisição um objeto JSON representando um usuário a ser editado, o
	 * end-point valida ele e tenta editar no id informado na url da requisição, se
	 * o processo ocorrer com sucesso retorna o usuário editado
	 * 
	 * @param id
	 *            id do usuário a ser editado
	 * @param usuario
	 *            objeto usuario com os valores a serem alterados
	 * @return ResponseEntity populado com o usuário editado
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> editarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario,
			BindingResult brUsuario) {
		try {
			// 200 - OK
			usuario.setId(id);
			Usuario.setParametros(new Usuario(), "id", "nome", "email", "dataNascimento", "permissao", "ativo");
			return ResponseEntity.ok(usuarioService.atualizar(usuario, brUsuario));
		} catch (UnprocessableEntityException e) {
			// 422 - UNPROCESSABLE ENTITY
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brUsuario));
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/usuario/{id do usuario a ser editado}/senha - Recebe
	 * no corpo da requisição um objeto JSON contendo uma senha representando a nova
	 * senha do usuário, o end-point valida ela e tenta editar no id informado na
	 * url da requisição, se o processo ocorrer com sucesso retorna o status 202.
	 * 
	 * @param id
	 *            id do usuário que editará a senha
	 * @param usuario
	 *            objeto usuario com os valores a serem alterados
	 * @param brUsuario
	 *            objeto populado com os possíveis erros de validação
	 * @return ResponseEntity populado com o usuário editado
	 */
	@PutMapping("/{id}/senha")
	public ResponseEntity<Object> editarSenhaUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario,
			BindingResult brUsuario) {
		try {
			// 200 - OK
			usuario.setId(id);
			usuarioService.atualizarSenha(usuario, brUsuario);
			return ResponseEntity.noContent().build();
		} catch (UnprocessableEntityException e) {
			// 422 - UNPROCESSABLE ENTITY
			List<String> campos = new LinkedList<>();
			campos.add("senha");
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brUsuario, campos));
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End-point de URL /api/v1/usuario/desativar/{id do usuario a ser desativado} -
	 * Recebe na url o id de um usuário a ser desativado, se o usuário existir
	 * desativa e retorna o usuário que foi desativado
	 * 
	 * @param id
	 *            id do usuário a ser desativado
	 * @return ResponseEntity populado com o usuário desativado
	 */
	@PutMapping("/desativar/{id}")
	public ResponseEntity<Object> desativarUsuario(@PathVariable Long id) {
		try {
			// 200 - OK
			Usuario.setParametros(new Usuario(), "id", "nome", "email", "dataNascimento", "permissao", "ativo");
			return ResponseEntity.ok(usuarioService.desativar(id));
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
