package br.com.lupus.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * <h1> Classe de configuração do Spring MVC </h1>
 * 
 * @author Mateus A.S
 */
@Configuration
@EnableWebMvc
@ComponentScan("br.com.lupus")
@Import(value = PersistenceConfig.class)
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * 
	 * 		Método que mapeia ao Spring onde estão os pacotes de recursos livres de acesso público (resources).
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
			.addResourceHandler("/assets/**")
			.addResourceLocations("/assets/");
		
		registry
			.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/");
	}
}
