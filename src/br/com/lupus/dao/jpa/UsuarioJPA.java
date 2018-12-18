package br.com.lupus.dao.jpa;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import br.com.lupus.dao.UsuarioDao;
import br.com.lupus.models.Usuario;

/**
 * 	UsuarioJPA que herda todos os métodos comuns aos DAO's da classe AbstractJPA
 * 
 * @author Mateus A.S
 */
@Repository("usuarioDao")
public class UsuarioJPA extends AbstractJPA<Usuario> implements UsuarioDao{
	
	@Override
	public String getNomeEntidade() {
		return "Usuario";
	}

	/**
	 * 	Implementação do método adicional do UsuarioDao que o AbstractJPA não supre.
	 * 	Retorna um usuário pelo email.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Usuario buscar(String email) {
		String hql = "FROM Usuario u WHERE u.email = :email";
		TypedQuery<Usuario> query = getSessao().createQuery(hql);
		query.setParameter("email", email);
		List<Usuario> lista =  query.getResultList();
		if(!lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
	/**
	 * 	Implementação do método adicional do UsuarioDao que o AbstractJPA não supre.
	 * 	Retorna um usuário pelo email e por sua senha.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Usuario buscar(String email, String senha) {
		String hql = "FROM Usuario u WHERE u.email = :email AND u.senhaCriptografada = :senha";
		TypedQuery<Usuario> query = getSessao().createQuery(hql);
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		List<Usuario> lista = (List<Usuario>) query.getResultList();
		if(!lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	/**
	 * 	Implementação do método adicional do UsuarioDao que o AbstractJPA não supre.
	 * 	Retorna uma lista de usuários pelo seu estado ativo.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscarTodos(boolean ativo) {
		String hql = "FROM Usuario u WHERE u.ativo = :ativo";
		TypedQuery<Usuario> query = getSessao().createQuery(hql);
		query.setParameter("ativo", ativo);
		List<Usuario> lista = (List<Usuario>) query.getResultList();
		if(!lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
