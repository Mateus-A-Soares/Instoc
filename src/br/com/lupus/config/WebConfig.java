package br.com.lupus.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * <h1> Classe de configuração do Spring Framework </h1>
 * <p> Mapeia todos os métodos de end point, e define a pasta com todas as páginas de retorno. </p>
 * 
 * @author Mateus A.S
 */
@Configuration
@EnableWebMvc
@ComponentScan("br.com.lupus")
@Import(value = PersistenceConfig.class)
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 *  	Método que define para o Spring MVC o prefixo e o sufixo de todo retorno dos end-point's mapeados,
	 *  como também o interpretador do arquivo de retorno (a ViewClass).
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		registry.viewResolver(resolver);
	}
	
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
