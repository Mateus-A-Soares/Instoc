package br.com.lupus.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.com.lupus.dao.UsuarioDao;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.exceptions.ValidationException;
import br.com.lupus.models.Usuario;

/**
 * 	Classe com os m�todos auxiliares referentes ao CRUD de usu�rios
 * 
 * @author Mateus A.S
 */
@Service
public class UsuarioService {
	@Autowired
	private UsuarioDao usuarioDao;

	/**
	 * 	M�todo que valida se existe um usu�rio com email e senha iguais aos passados.
	 * V�lida se h� algum erro de valida��o referente ao email e a senha, e se n�o,
	 * procura na base de dados se o usu�rio existe
	 * 
	 * @param usuario objeto populado com o email e a senha procurados
	 * @param brUsuario objeto populado com poss�veis erros de valida��o
	 * @return usuario procurado populado com todas as informa��es necess�rias
	 * @throws ValidationException exception disparada se h� erros de valida��o em um dos dois campos (email ou senha) 
	 * @throws EntityNotFound exception disparada se n�o existir usu�rio com o email e senha passados
	 */
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

	/**
	 * 	M�todo que busca um usu�rio pelo seu ID
	 * @param id n�mero de identifica��o usado na procura
	 * @return usu�rio referente ao id
	 * @throws EntityNotFound
	 */
	public Usuario buscar(Long id) throws EntityNotFound {

		Usuario usuario = usuarioDao.buscar(id);
		if (usuario == null)
			throw new EntityNotFound();
		return usuario;
	}

	/**
	 * 	M�todo que retorna todos os usu�rios cadastrados no sistema
	 * 
	 * @return lista de usu�rios cadastrados
	 */
	public List<Usuario> buscarTodos() {

		return usuarioDao.buscarTodos();
	}

	/**
	 * 	M�todo que cadastra um usu�rio na base de dados.
	 * 	Recebe um usu�rio, verifica se o email j� foi cadastrado, se os campos est�o nulos
	 * e poss�veis erros de valida��o.
	 *  Caso n�o haja erros de valida��o, cadastra o usu�rio na base de dados. 
	 * @param usuario usu�rio a ser cadastrado
	 * @param brUsuario objeto populado com os poss�veis erros de valida��o
	 * @throws UnprocessableEntityException exception disparada quando h� erros de valida��o
	 */
	public void salvar(Usuario usuario, BindingResult brUsuario) throws UnprocessableEntityException {
		if (usuarioDao.getEmail(usuario.getEmail()) != null)
			brUsuario.addError(new FieldError("usuario", "email", "endere�o de email j� cadastrado"));

		if (usuario.getEmail() == null)
			brUsuario.addError(new FieldError("usuario", "email", "endere�o de email n�o pode estar nulo"));

		if (usuario.getPermissao() == null)
			brUsuario.addError(new FieldError("usuario", "permissao", "permiss�o n�o pode estar nula"));

		if (usuario.getDataNascimento() == null)
			brUsuario.addError(new FieldError("usuario", "dataNascimento", "data de nascimento n�o pode estar nula"));

		if (usuario.getNome() == null)
			brUsuario.addError(new FieldError("usuario", "nome", "nome n�o pode estar nulo"));

		if (usuario.getSenha() == null)
			brUsuario.addError(new FieldError("usuario", "senha", "senha n�o pode estar nula"));

		if (brUsuario.hasFieldErrors()) {
			throw new UnprocessableEntityException();
		} else {
			usuarioDao.inserir(usuario);
		}
	}
	
	/**
	 * 	M�todo que edita um usu�rio cadastrado na base de dados.
	 * 	Recebe um usu�rio, verifica se o email j� foi cadastrado e poss�veis erros de valida��o.
	 *	Caso existam campos nulos, popula o campo com o antigo valor.
	 *  Caso n�o haja erros de valida��o, edita o usu�rio na base de dados. 
	 * @param usuario usu�rio a ser editado
	 * @param brUsuario objeto populado com os poss�veis erros de valida��o
	 * @throws UnprocessableEntityException exception disparada quando h� erros de valida��o
	 */
	public Usuario editar(Usuario usuario, BindingResult brUsuario) throws UnprocessableEntityException {
		Usuario u = usuarioDao.getEmail(usuario.getEmail());
		if (u != null && u.getId() != usuario.getId()) {
			brUsuario.addError(new FieldError("usuario", "email", "endere�o de email j� cadastrado"));
		}
		if (brUsuario.hasFieldErrors()) {
			throw new UnprocessableEntityException();
		} else {
			u = usuarioDao.buscar(usuario.getId());
			if (usuario.getAtivo() == null)
				usuario.setAtivo(u.getAtivo());
			if (usuario.getPermissao() == null)
				usuario.setPermissao(u.getPermissao());
			if (usuario.getDataNascimento() == null)
				usuario.setDataNascimento(u.getDataNascimento());
			if (usuario.getEmail() == null)
				usuario.setEmail(u.getEmail());
			if (usuario.getNome() == null)
				usuario.setNome(u.getNome());
			if (usuario.getSenha() == null)
				usuario.setSenha(u.getSenha());
			usuarioDao.alterar(usuario);
		}
		return usuario;
	}

	/**
	 * 	M�todo que desativa um usu�rio cadastrado na base de dados, ou seja,
	 * define seu status ativo como falso
	 * 
	 * @param id id do usu�rio a ser desativado
	 * @return usu�rio desativado
	 * @throws EntityNotFound exception disparada quando o id n�o corresponde a nenhum usu�rio
	 */
	public Usuario desativar(Long id) throws EntityNotFound {
		Usuario u = usuarioDao.buscar(id);
		if (u == null) {
			throw new EntityNotFound();
		} else {
			u.setAtivo(false);
			usuarioDao.alterar(u);
			return u;
		}
	}
}
