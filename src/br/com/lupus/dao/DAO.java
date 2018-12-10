package br.com.lupus.dao;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 	Interface abstrata dos métodos utilizados para o CRUD das entidades
 *
 * @param <T> modelo representativo da entidade a ser realizado o CRUD
 * @author Mateus A.S
 */
public interface DAO<T> {

	@Transactional
	public T buscar(Long id);

	@Transactional
	public List<T> buscarTodos();

	@Transactional
	public void atualizar(T obj);

	@Transactional
	public void deletar(Long id);

	@Transactional
	public void deletar(T obj);

	@Transactional
	public void persistir(T obj);
}
