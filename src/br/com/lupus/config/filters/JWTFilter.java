package br.com.lupus.config.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import br.com.lupus.models.Usuario;
import br.com.lupus.utils.JwtUtils;

/**
 * 	Filtro utilizado para validar se existe um token na requisição, e se sim, validar o próprio token
 * 
 * 	@author Mateus A.S
 */
@Component
public class JWTFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String authorization = request.getHeader("authorization");
		if (authorization != null) {

			if (authorization.matches("(Bearer )(\\w|\\.|\\-)+")) {
				String token = authorization.split(" ")[1];

				try {
					JwtUtils.validarToken(token);
					Usuario usuarioToken = JwtUtils.getUsuarioToken(token);
					SecurityContextHolder.getContext().setAuthentication(usuarioToken);
				} catch (Exception e) {
					// Token inválido
				}
			} else {
				// Formato do Token inválido
			}
		} else {
			// Token nulo
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {		
	}
}
