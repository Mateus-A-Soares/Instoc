package br.com.lupus.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.ValidationException;
import br.com.lupus.models.Usuario;
import br.com.lupus.services.UsuarioService;
import br.com.lupus.utils.BindingResultUtils;
import br.com.lupus.utils.JwtUtils;

/**
 * 	Controller de autentica��o
 * 	Cont�m um end-point que recebe um usuario e valida ele,
 * caso a valida��o for bem sucedida, retorna um 
 * token ao usu�rio de acesso a aplica��o.
 * 
 * @author Mateus A.S
 */
@RestController
@RequestMapping(value = "/api/v1")
public class AutenticacaoController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 *	End point de autentica��o.
	 *	Recebe um usu�rio, valida ele para poder retornar um token ao usu�rio de acesso a aplica��o.
	 *
	 * @param usuario usu�rio populado com o email e a senha para tentativa de autentica��o
	 * @param brUsuario BindingResult com poss�veis erros de valida��o
	 * @return token
	 */
	@PostMapping("/jwt")
	public ResponseEntity<Object> autentica(@Valid @RequestBody Usuario usuario, BindingResult brUsuario){
		try {
			// 200 - OK
			Usuario authUser = usuarioService.getEmailSenha(usuario, brUsuario);
			Map<String, String> tokenMap = new HashMap<>();
			tokenMap.put("token", JwtUtils.getToken(authUser));
			return ResponseEntity.ok(tokenMap);
		} catch (ValidationException e) {
			// 422 - UNPROCESSABLE  ENTITY
			List<String> fields = new ArrayList<>();
			fields.add("email");
			fields.add("senha");
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brUsuario, fields));
		} catch (EntityNotFound e) {
			// 404 - NOT FOUND
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
