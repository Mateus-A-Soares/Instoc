package br.com.lupus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.services.TipoItemTagService;

/**
 * Controller com os end-points relacionados ao CRUD de usuários no sistema
 * Necessita de permissão de administrador para acessar qualquer um dos
 * end-points
 * 
 * @author Mateus A.S
 */
@RestController
@RequestMapping("/api/v1/item/tipo/tag")
public class TipoItemTagController {

	@Autowired
	private TipoItemTagService tagService;

	/**
	 * End-point de URL /api/v1/item/tipo/tag/{id da tag a ser deletada} - Recebe na
	 * URL o id de uma tag a ser excluida, retornando no ResponseEntity o status
	 * 202 se tudo ocorrer bem, 404 se a tag referente não existir e 500 para outros possíveis
	 * erros não tratados
	 * 
	 * @param id
	 *            id da tag a ser excluida
	 * @return ResponseEntity com status 202 (OK - NO CONTENT), 404 (NOT FOUND) ou 500 (INTERNAL SERVER ERROR)
	 */
	@DeleteMapping("/{id}")
	private ResponseEntity<Object> deletarTag(@PathVariable Long id) {
		try {
			// 202 - NO CONTENT / OK
			tagService.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFound e) {
			// 404 - NOT FOUND
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// 500 - INTERNAL SERVER ERROR
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
