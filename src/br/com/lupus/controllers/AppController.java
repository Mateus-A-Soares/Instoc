package br.com.lupus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <h1> AppController </h1>
 * 
 * <p> Classe que contem end points gerais utilizados
 * na aplica��o quando o usu�rio ainda n�o est� logado. </p>
 * 
 * @author Mateus A.S
 *
 */
@Controller
@RequestMapping(value = {"", "/"})
public class AppController {

	/**
	 *  <p> Endpoint de acesso a p�gina Index.</p>
	 *  <p> Quando a url / � acessada esse m�todo � chamado pelo Spring, retornando
	 *  a localiza��o da p�gina index dentro das pastas WebContent/WEB-INF/views.</p>
	 *  <p> Com a localiza��o do arquivo index.jsp, o Spring pode retorna-lo
	 *  ao usu�rio.</p>
	 *  
	 * @return String referente a localiza��o do arquivo index.jsp dentro da pasta views.
	 */
	@GetMapping
	public String index() {
		
		return "index";
	}
}
