package br.com.lupus.controllers;

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

import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.models.Usuario;
import br.com.lupus.services.UsuarioService;
import br.com.lupus.utils.BindingResultUtils;

/**
 * 	Controller com os end-points relacionados ao CRUD de usuários no sistema
 * Necessita de permissão de administrador para acessar qualquer um dos end-points
 * 
 * @author Mateus A.S
 */
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * 	End-point de URL /api/v1/usuario
	 * 	Retorna ao cliente que fez a requisição um array de objetos JSON representando os usuários do sistema
	 * 
	 * @return ResponseEntity populado com os usuários cadastrados no sistema
	 */
	@GetMapping("")
	public ResponseEntity<Object> listarUsuarios(){
		try {
			
			return ResponseEntity.ok(usuarioService.buscarTodos());
		}catch (Exception e) {
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 	End-point de URL /api/v1/usuario/{id do usuario procurado}
	 * 	Retorna ao cliente que fez a requisição um objeto JSON representando o usuário solicitado
	 * 
	 * @param id id do usuário solicitado
	 * @return ResponseEntity populado com o usuário solicitado
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> acharUsuario(@PathVariable Long id){
		try {
			
			return ResponseEntity.ok(usuarioService.buscar(id));
		}catch(EntityNotFound e) {
			
			return ResponseEntity.notFound().build();
		}catch (Exception e) {
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 	End-point de URL /api/v1/usuario
	 * 	Recebe no corpo da requisição um objeto JSON representando um usuário a ser salvo,
	 * o end-point valida ele e tenta persistir, se o processo ocorrer com sucesso retorna
	 * um status 204
	 * 
	 * @param id id do usuário solicitado
	 * @return ResponseEntity populado com o usuário solicitado
	 */
	@PostMapping("")
	public ResponseEntity<Object> cadastraUsuario(@Valid @RequestBody Usuario usuario, BindingResult brUsuario){
		try {
			
			usuarioService.salvar(usuario, brUsuario);
			return ResponseEntity.noContent().build();
		}catch (UnprocessableEntityException e) {

			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brUsuario));
		}catch (Exception e) {
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 	End-point de URL /api/v1/usuario/{id do usuario a ser editado}
	 * 	Recebe no corpo da requisição um objeto JSON representando um usuário a ser editado,
	 * o end-point valida ele e tenta editar no id informado na url da requisição, se o processo
	 * ocorrer com sucesso retorna o usuário editado
	 * 
	 * @param id id do usuário solicitado
	 * @return ResponseEntity populado com o usuário solicitado
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> editarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult brUsuario){
		try {
			
			usuario.setId(id);
			return ResponseEntity.ok(usuarioService.editar(usuario, brUsuario));
		}catch (UnprocessableEntityException e) {

			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brUsuario));
		}catch (Exception e) {
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 	End-point de URL /api/v1/usuario/desativar/{id do usuario a ser desativado}
	 * 	Recebe na url o id de um usuário a ser desativado, se o usuário existir desativa
	 *  e retorna o usuário que foi desativado
	 * 
	 * @param id id do usuário solicitado
	 * @return ResponseEntity populado com o usuário solicitado
	 */
	@PutMapping("/desativar/{id}")
	public ResponseEntity<Object> desativarUsuario(@PathVariable Long id){
		try {
			
			return ResponseEntity.ok(usuarioService.desativar(id));
		}catch (Exception e) {
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
