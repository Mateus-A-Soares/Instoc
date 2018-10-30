package br.com.lupus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lupus.dao.UsuarioDao;

@RestController
@RequestMapping(value = {"", "/"})
public class AutenticacaoController {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@GetMapping("/auth")
	public ResponseEntity<Object> teste(){
		
		return ResponseEntity.ok(usuarioDao.buscarTodos());
	}
}
