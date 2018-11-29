package br.com.lupus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lupus.models.Ambiente;
import br.com.lupus.models.Item;
import br.com.lupus.models.TipoItem;
import br.com.lupus.models.Usuario;
import br.com.lupus.services.AmbienteService;

/**
 * 	Controller com os end-points relacionados ao CRUD de ambientes no sistema
 * 
 * @author Mateus A.S
 */
@RestController
@RequestMapping("/api/v1/ambiente")
public class AmbienteController {
	
	@Autowired
	public AmbienteService ambienteService;
	
	/**
	 * 	End-point de URL /api/v1/ambiente
	 * 	Retorna ao cliente que fez a requisição um array de objetos JSON representando os ambientes cadastrados no sistema
	 * 
	 * @return ResponseEntity populado com os ambientes cadastrados no sistema
	 */
	@GetMapping
	public ResponseEntity<Object> listarAmbientes(){
		try {
			// 200 - OK
			Ambiente.setParametros(new Ambiente(), "id", "descricao", "cadastrante", "itens");
			Usuario.setParametros(new Usuario(), "nome", "email");
			Item.setParametros(new Item(), "tipo");
			TipoItem.setParametros(new TipoItem(), "nome");
			return ResponseEntity.ok(ambienteService.listarAmbientes());
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
