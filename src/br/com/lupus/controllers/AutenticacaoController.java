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

import br.com.lupus.exceptions.EntityNotFoundException;
import br.com.lupus.exceptions.ForbbidenException;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.models.Usuario;
import br.com.lupus.services.UsuarioService;
import br.com.lupus.utils.BindingResultUtils;
import br.com.lupus.utils.JwtUtils;

/**
 * Controller de autenticação Contém um end-point que recebe um usuario e valida
 * ele, caso a validação for bem sucedida, retorna um token ao usuário de acesso
 * a aplicação.
 * 
 * @author Mateus A.S
 */
@RestController
@RequestMapping(value = "/api/v1")
public class AutenticacaoController {

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * End point de URL /api/v1/jwt Recebe um usuário na requisição, valida ele para
	 * poder retornar um token de acesso ao cliente
	 *
	 * @param usuario
	 *            usuário populado com o email e a senha para tentativa de
	 *            autenticação
	 * @param brUsuario
	 *            BindingResult com possíveis erros de validação
	 * @return token
	 */
	@PostMapping("/jwt")
	public ResponseEntity<Object> autenticar(@Valid @RequestBody Usuario usuario, BindingResult brUsuario) {
		try {
			// 200 - OK
			System.out.println(usuario.toString());
			Usuario authUser = usuarioService.getEmailSenha(usuario, brUsuario);
			Map<String, String> tokenMap = new HashMap<>();
			tokenMap.put("token", JwtUtils.getToken(authUser));
			return ResponseEntity.ok(tokenMap);
		} catch (ForbbidenException e) {
			// 403 - FORBIDDEN
			List<String> fields = new ArrayList<>();
			fields.add("ativo");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BindingResultUtils.toHashMap(brUsuario, fields));
		} catch (EntityNotFoundException e) {
			// 404 - NOT FOUND
			return ResponseEntity.notFound().build();
		} catch (UnprocessableEntityException e) {
			// 422 - UNPROCESSABLE ENTITY
			List<String> fields = new ArrayList<>();
			fields.add("email");
			fields.add("senha");
			return ResponseEntity.unprocessableEntity().body(BindingResultUtils.toHashMap(brUsuario, fields));
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
