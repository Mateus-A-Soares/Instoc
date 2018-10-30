package br.com.lupus.controllers;

import java.util.HashMap;
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

import br.com.lupus.config.utils.JwtUtils;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.ValidationException;
import br.com.lupus.models.Usuario;
import br.com.lupus.services.UsuarioService;

@RestController
@RequestMapping(value = "/api/v1")
public class AutenticacaoController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/jwt")
	public ResponseEntity<Object> teste(@Valid @RequestBody Usuario usuario, BindingResult brUsuario){
		try {
			Usuario authUser = usuarioService.getEmalSenha(usuario, brUsuario);
			Map<String, String> tokenMap = new HashMap<>();
			tokenMap.put("token", JwtUtils.getToken(authUser));
			return ResponseEntity.ok(tokenMap);
		} catch (ValidationException e) {
			return ResponseEntity.unprocessableEntity().build();
		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
