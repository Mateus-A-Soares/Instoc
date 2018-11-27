package br.com.lupus.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.com.lupus.dao.UsuarioDao;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.exceptions.ValidationException;
import br.com.lupus.models.Usuario;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioDao usuarioDao;

	public Usuario getEmailSenha(Usuario usuario, BindingResult brUsuario) throws ValidationException, EntityNotFound {

		if (brUsuario.hasFieldErrors("email") || brUsuario.hasFieldErrors("senha")) {

			throw new ValidationException();
		} else {

			Usuario usuarioAutenticado = usuarioDao.getEmailSenha(usuario.getEmail(), usuario.getSenha());
			if (usuarioAutenticado == null) {

				throw new EntityNotFound();
			} else {

				return usuarioAutenticado;
			}
		}
	}

	public Usuario valida(Usuario usuario, BindingResult brUsuario) throws ValidationException, EntityNotFound {

		if (brUsuario.hasFieldErrors("email") || brUsuario.hasFieldErrors("senha")) {

			throw new ValidationException();
		} else {

			Usuario usuarioAutenticado = usuarioDao.getEmailSenha(usuario.getEmail(), usuario.getSenha());
			if (usuarioAutenticado == null) {

				throw new EntityNotFound();
			} else {

				return usuarioAutenticado;
			}
		}
	}

	public Usuario buscar(Long id) throws EntityNotFound {

		Usuario usuario = usuarioDao.buscar(id);
		if (usuario == null)
			throw new EntityNotFound();
		return usuario;
	}

	public List<Usuario> buscarTodos() {

		return usuarioDao.buscarTodos();
	}

	public void salvar(@Valid Usuario usuario, BindingResult brUsuario) throws UnprocessableEntityException {

		if (usuarioDao.buscarEmail(usuario.getEmail()) != null) {
			brUsuario.addError(new FieldError("usuario", "email", "endereço de email já cadastrado"));
		}
		if (brUsuario.hasFieldErrors()) {
			throw new UnprocessableEntityException();
		} else {
			usuarioDao.inserir(usuario);
		}
	}

	public Usuario editar(@Valid Usuario usuario, BindingResult brUsuario) throws UnprocessableEntityException {
		Usuario u = usuarioDao.buscarEmail(usuario.getEmail());
		if (u != null && u.getId() != usuario.getId()) {
			brUsuario.addError(new FieldError("usuario", "email", "endereço de email já cadastrado"));
		}
		if (brUsuario.hasFieldErrors()) {
			throw new UnprocessableEntityException();
		} else {
			usuarioDao.alterar(usuario);
		}
		return usuario;
	}

	public Usuario desativar(Long id) throws EntityNotFound {
		Usuario u = usuarioDao.buscar(id);
		if(u == null) {
			throw new EntityNotFound();
		}else {
			u.setAtivo(false);
			usuarioDao.alterar(u);
			return u;
		}
	}
}
