package br.com.lupus.dao.jpa;

import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.lupus.dao.DAO;

/**
 * 	Classe abstrata que substitui a implementação dos métodos comuns de todos
 * os DAO's, como inserir, alterar, deletar, buscar por id e buscar todos.
 * 
 * @param <T> POJO representativo de uma entidade
 * 
 * @author Mateus A.S 
 */
@Repository
@Transactional
public abstract class AbstractJPA<T> implements DAO<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * @return retorna a sessão com o banco
	 */
	public Session getSessao() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 	Método que deve ser implementado na instância dessa classe.
	 * Deve retornar o nome da entidade que o placeholder representa.
	 * @return o nome da entidade que o placeholder representa.
	 */
	public abstract String getNomeEntidade();

	/**
	 * Insere um registro na entidade representada pelo placeholder
	 */
	@Override
	public void inserir(T obj) {
		getSessao().persist(obj);
	}
	
	/**
	 * Edita um registro na entidade representada pelo placeholder
	 */
	@Override
	public void alterar(T obj) {
		getSessao().update(obj);
	}

	/**
	 * Deleta um registro na entidade representada pelo placeholder através do id
	 */
	@Override
	public void deletar(Long id) {
		getSessao().delete(buscar(id));
	}

	/** 
	 * Edita um registro na entidade representada pelo placeholder através do objeto
	 */
	@Override
	public void deletar(T obj) {
		getSessao().delete(obj);
	}

	/**
	 * 	Retorna um modelo representativo da entidade definida pelo placeholder através de seu id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T buscar(Long id) {
		String hql = "FROM " + getNomeEntidade() + " o WHERE o.id = :id";
		TypedQuery<T> query = getSessao().createQuery(hql);
		query.setParameter("id", id);
		List<T> lista = (List<T>) query.getResultList();
		if(!lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	/**
	 * Retona todos os registros da entidade defina pelo placeholder populadas no modelo definido pelo placeholder
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarTodos() {
		String hql = "FROM " + getNomeEntidade();
		TypedQuery<T> query = getSessao().createQuery(hql);
		return query.getResultList();
	}

}
