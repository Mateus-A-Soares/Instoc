package br.com.lupus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.lupus.dao.UsuarioDao;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.ValidationException;
import br.com.lupus.models.Usuario;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioDao usuarioDao;
	
	public Usuario getEmalSenha(Usuario usuario, BindingResult brUsuario) throws ValidationException, EntityNotFound {
		
		if(brUsuario.hasErrors()) {
			
			throw new ValidationException();
		}else {
			
			Usuario usuarioAutenticado = usuarioDao.getEmailSenha(usuario.getEmail(), usuario.getSenha());
			if(usuarioAutenticado == null) {
				
				throw new EntityNotFound();
			}else{
			
				return usuarioAutenticado;
			}
		}
	}
}
