package br.com.lupus.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.lupus.dao.UsuarioDao;
import br.com.lupus.models.Usuario;

@Repository("usuarioDao")
public class UsuarioJPA extends AbstractJPA<Usuario> implements UsuarioDao{
	
	@Override
	public String getNomeEntidade() {
		return "Usuario";
	}

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
