package br.com.lupus.dao.jpa;

import java.util.List;

import org.hibernate.Query;
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
	
	/**
	 * 	Retorna o nome da entidade que o usuario representa para a classe pai
	 */
	@Override
	public String getNomeEntidade() {
		return "Usuario";
	}

	/**
	 * 	Implementação do método adicional do UsuarioDao que o AbstractJPA não supre.
	 * 	Retorna um usuário pelo email e pos sua senha.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Usuario getEmailSenha(String email, String senha) {
		String hql = "FROM Usuario u WHERE u.email = :email AND u.senha = :senha";
		Query query = getSessao().createQuery(hql);
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		List<Usuario> lista = (List<Usuario>) query.list();
		if(!lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}
