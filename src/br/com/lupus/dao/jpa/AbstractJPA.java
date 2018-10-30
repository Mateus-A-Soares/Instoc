package br.com.lupus.dao.jpa;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.lupus.dao.DAO;

@Repository
@Transactional
public abstract class AbstractJPA<T> implements DAO<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	public Session getSessao() {
		return sessionFactory.getCurrentSession();
	}

	public abstract String getNomeEntidade();

	@Override
	public void inserir(T obj) {
		getSessao().persist(obj);
	}
	
	@Override
	public void alterar(T obj) {
		getSessao().update(obj);
	}

	@Override
	public void deletar(Long id) {
		getSessao().delete(buscar(id));
	}

	@Override
	public void deletar(T obj) {
		getSessao().delete(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T buscar(Long id) {
		String hql = "FROM " + getNomeEntidade() + " o WHERE o.id = :id";
		Query query = getSessao().createQuery(hql);
		query.setParameter("id", id);
		List<T> lista = (List<T>) query.list();
		if(!lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarTodos() {
		String hql = "FROM " + getNomeEntidade();
		Query query = getSessao().createQuery(hql);
		return (List<T>) query.list();
	}

}
