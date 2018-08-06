package br.com.lupus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <h1> AppController </h1>
 * 
 * <p> Classe que contem end points gerais utilizados
 * na aplicação quando o usuário ainda não está logado. </p>
 * 
 * @author Mateus A.S
 *
 */
@Controller
@RequestMapping(value = {"", "/"})
public class AppController {

	/**
	 *  <p> Endpoint de acesso a página Index.</p>
	 *  <p> Quando a url / é acessada esse método é chamado pelo Spring, retornando
	 *  a localização da página index dentro das pastas WebContent/WEB-INF/views.</p>
	 *  <p> Com a localização do arquivo index.jsp, o Spring pode retorna-lo
	 *  ao usuário.</p>
	 *  
	 * @return String referente a localização do arquivo index.jsp dentro da pasta views.
	 */
	@GetMapping
	public String index() {
		
		return "index";
	}
}
