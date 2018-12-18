package br.com.lupus.services;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.com.lupus.dao.UsuarioDao;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.models.Usuario;

/**
 * Classe com os métodos auxiliares referentes ao CRUD de usuários
 * 
 * @author Mateus A.S
 */
@Service
public class UsuarioService {
	@Autowired
	private UsuarioDao usuarioDao;

	/**
	 * Método que valida se existe um usuário com email e senha iguais aos passados.
	 * Válida se há algum erro de validação referente ao email e a senha, e se não,
	 * procura na base de dados se o usuário existe
	 * 
	 * @param usuario
	 *            objeto populado com o email e a senha procurados
	 * @param brUsuario
	 *            objeto populado com possíveis erros de validação
	 * @return usuario procurado populado com todas as informações necessárias
	 * @throws ValidationException
	 *             exception disparada se há erros de validação em um dos dois
	 *             campos (email ou senha)
	 * @throws EntityNotFound
	 *             exception disparada se não existir usuário com o email e senha
	 *             passados
	 */
	public Usuario getEmailSenha(Usuario usuario, BindingResult brUsuario) throws UnprocessableEntityException, EntityNotFound {

		if (brUsuario.hasFieldErrors("email") || brUsuario.hasFieldErrors("senha")) {

			throw new UnprocessableEntityException();
		} else {

			Usuario usuarioAutenticado = usuarioDao.buscar(usuario.getEmail(), usuario.getSenhaCriptografada());
			if (usuarioAutenticado == null) {

				throw new EntityNotFound();
			} else {

				return usuarioAutenticado;
			}
		}
	}

	/**
	 * Método que busca um usuário pelo seu ID
	 * 
	 * @param id
	 *            número de identificação usado na procura
	 * @return usuário referente ao id
	 * @throws EntityNotFound
	 */
	public Usuario buscar(Long id) throws EntityNotFound {

		Usuario usuario = usuarioDao.buscar(id);
		if (usuario == null)
			throw new EntityNotFound();
		return usuario;
	}

	/**
	 * Método que retorna todos os usuários cadastrados no sistema
	 * 
	 * @return lista de usuários cadastrados
	 */
	public List<Usuario> buscarTodos() {

		return usuarioDao.buscarTodos();
	}

	/**
	 * Método que cadastra um usuário na base de dados. Recebe um usuário, verifica
	 * se o email já foi cadastrado, se os campos estão nulos e possíveis erros de
	 * validação. Caso não haja erros de validação, cadastra o usuário na base de
	 * dados.
	 * 
	 * @param usuario
	 *            usuário a ser cadastrado
	 * @param brUsuario
	 *            objeto populado com os possíveis erros de validação
	 * @throws UnprocessableEntityException
	 *             exception disparada quando há erros de validação
	 */
	public void persistir(Usuario usuario, BindingResult brUsuario) throws UnprocessableEntityException {
		usuario.setAtivo(true);
		if (usuarioDao.buscar(usuario.getEmail()) != null)
			brUsuario.addError(new FieldError("usuario", "email", "endereço de email já cadastrado"));

		if (usuario.getEmail() == null)
			brUsuario.addError(new FieldError("usuario", "email", "endereço de email não pode estar nulo"));

		if (usuario.getPermissao() == null)
			brUsuario.addError(new FieldError("usuario", "permissao", "permissão não pode estar nula"));

		if (usuario.getDataNascimento() == null)
			brUsuario.addError(new FieldError("usuario", "dataNascimento", "data de nascimento não pode estar nula"));

		if (usuario.getNome() == null)
			brUsuario.addError(new FieldError("usuario", "nome", "nome não pode estar nulo"));

		if (usuario.getSenha() == null)
			brUsuario.addError(new FieldError("usuario", "senha", "senha não pode estar nula"));

		if (brUsuario.hasFieldErrors()) {
			throw new UnprocessableEntityException();
		} else {
			usuarioDao.persistir(usuario);
		}
	}

	/**
	 * Método que edita um usuário cadastrado na base de dados. Recebe um usuário,
	 * verifica se o email já foi cadastrado e possíveis erros de validação. Caso
	 * existam campos nulos, popula o campo com o antigo valor. Caso não haja erros
	 * de validação, edita o usuário na base de dados.
	 * 
	 * @param usuario
	 *            usuário a ser editado
	 * @param brUsuario
	 *            objeto populado com os possíveis erros de validação
	 * @throws UnprocessableEntityException
	 *             exception disparada quando há erros de validação
	 */
	public Usuario atualizar(Usuario usuario, BindingResult brUsuario) throws UnprocessableEntityException, EntityNotFoundException {
		Usuario usuarioAntigo = usuarioDao.buscar(usuario.getEmail());
		if (usuarioAntigo != null && usuarioAntigo.getId() != usuario.getId()) {
			brUsuario.addError(new FieldError("usuario", "email", "endereço de email já cadastrado"));
		}
		if (brUsuario.hasFieldErrors()) {
			throw new UnprocessableEntityException();
		} else {
			usuarioAntigo = usuarioDao.buscar(usuario.getId());
			if (usuarioAntigo == null)
				throw new EntityNotFoundException();
			if (usuario.getAtivo() != null)
				usuarioAntigo.setAtivo(usuario.getAtivo());
			if (usuario.getPermissao() != null)
				usuarioAntigo.setPermissao(usuario.getPermissao());
			if (usuario.getDataNascimento() != null)
				usuarioAntigo.setDataNascimento(usuario.getDataNascimento());
			if (usuario.getEmail() != null)
				usuarioAntigo.setEmail(usuario.getEmail());
			if (usuario.getNome() != null)
				usuarioAntigo.setNome(usuario.getNome());
			if (usuario.getSenha() != null)
				usuarioAntigo.setSenha(usuario.getSenha());
			usuarioDao.atualizar(usuarioAntigo);
		}
		return usuarioAntigo;
	}

	/**
	 * Método que desativa um usuário cadastrado na base de dados, ou seja, define
	 * seu status ativo como falso
	 * 
	 * @param id
	 *            id do usuário a ser desativado
	 * @return usuário desativado
	 * @throws EntityNotFound
	 *             exception disparada quando o id não corresponde a nenhum usuário
	 */
	public Usuario desativar(Long id) throws EntityNotFound {
		Usuario usuarioAntigo = usuarioDao.buscar(id);
		if (usuarioAntigo == null) {
			throw new EntityNotFound();
		} else {
			usuarioAntigo.setAtivo(false);
			usuarioDao.atualizar(usuarioAntigo);
			return usuarioAntigo;
		}
	}
}
