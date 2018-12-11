package br.com.lupus.services;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.com.lupus.dao.TipoItemDao;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.models.TipoItem;
import br.com.lupus.models.TipoItemTag;
import br.com.lupus.models.Usuario;

/**
 * Classe com os métodos auxiliares referentes ao CRUD de tipo-itens
 * 
 * @author Mateus A.S
 */
@Service
public class TipoItemService {

	@Autowired
	private TipoItemDao tipoItemDao;

	/**
	 * Método que retorna uma lista com todos os tipos-itens cadastrados na base de
	 * dados
	 * 
	 * @return lista de tipo-itens cadastrados
	 */
	public List<TipoItem> buscarTipos() {
		return tipoItemDao.buscarTodos();
	}

	/**
	 * Método que retorna um tipo-item cadastrado na base de dados através do seu id
	 * 
	 * @param id
	 *            id do tipo-item procurado
	 * @return objeto tipo-item populado
	 * @throws EntityNotFound
	 *             disparada quando não existe tipo-item referenciado ao id passado
	 */
	@Transactional(value = TxType.REQUIRED)
	public TipoItem buscaTipo(Long id) throws EntityNotFound {
		TipoItem tipoItem = tipoItemDao.buscar(id);
		if (tipoItem == null)
			throw new EntityNotFound();
		Hibernate.initialize(tipoItem.getCadastrante());
		Hibernate.initialize(tipoItem.getTagsAnexadas());
		Hibernate.initialize(tipoItem.getItensAnexados());
		return tipoItem;
	}

	/**
	 * Método que persiste um tipo-item na base de dados. Recebe um objeto TipoItem
	 * populado, verifica se há erros de validação no tipo e nas suas tags (se
	 * houver no item dispara uma exceção, se houver na tag a descarta) define o
	 * usuário logado como o cadastrante do tipo-item e efetua a persistência
	 * 
	 * @param tipoItem
	 *            objeto tipoItem populado
	 * @param brTipo
	 *            objeto populado com os possíveis erros de validação
	 * @throws UnprocessableEntityException
	 *             disparada se houver erros de validação
	 */
	public void persistirTipo(TipoItem tipoItem, BindingResult brTipo) throws UnprocessableEntityException {
		if (tipoItem.getNome() == null)
			brTipo.addError(new FieldError("tipoItem", "nome", "O nome não pode estar vazio"));
		if (brTipo.hasFieldErrors())
			throw new UnprocessableEntityException();
		if (tipoItem.getTagsAnexadas() != null) {
			Iterator<TipoItemTag> tags = tipoItem.getTagsAnexadas().iterator();
			List<TipoItemTag> tagsAnexadas = new LinkedList<>();
			while (tags.hasNext()) {
				TipoItemTag tag = tags.next();
				if (tag.getCabecalho() != null && tag.getCabecalho().length() <= 50 && tag.getCorpo() != null
						&& tag.getCorpo().length() <= 50) {
					tag.setTipoItem(tipoItem);
					tag.setTipo(null);
					tagsAnexadas.add(tag);
				}
			}
			tipoItem.setTagsAnexadas(tagsAnexadas);
		}
		tipoItem.setCadastrante((Usuario) SecurityContextHolder.getContext().getAuthentication());
		tipoItemDao.persistir(tipoItem);
	}

	/**
	 * Método que atualiza um tipo-item cadastrado na base de dados. Recebe um
	 * objeto TipoItem populado, verifica se há erros de validação, se não houver,
	 * efetua a atualização
	 * 
	 * @param tipoItem
	 *            objeto populado com os novos valores a serem atualizados
	 * @param brTipo
	 *            objeto populado com os possíveis erros de validação
	 * @return objeto TipoItem populado com o registro que foi atualizado
	 * @throws EntityNotFound
	 *             disparada se o registro a ser editado não for encontrado
	 * @throws UnprocessableEntityException
	 *             disparada se houver erros de validação
	 * 
	 */
	@Transactional(value = TxType.REQUIRED)
	public TipoItem editarTipo(@Valid TipoItem tipoItem, BindingResult brTipo)
			throws EntityNotFound, UnprocessableEntityException {
		TipoItem tipoItemAntigo = tipoItemDao.buscar(tipoItem.getId());
		if (tipoItemAntigo == null)
			throw new EntityNotFound();
		if (brTipo.hasFieldErrors())
			throw new UnprocessableEntityException();
		if (tipoItem.getNome() != null)
			tipoItemAntigo.setNome(tipoItem.getNome());
		tipoItemDao.atualizar(tipoItemAntigo);
		Hibernate.initialize(tipoItemAntigo.getTagsAnexadas());
		Hibernate.initialize(tipoItemAntigo.getItensAnexados());
		Hibernate.initialize(tipoItemAntigo.getCadastrante());
		return tipoItemAntigo;
	}

	/**
	 * Método que exclui fisicamente um tipo-item cadastrado na base de dados.
	 * Verifica se existe tipo-item referente ao id passado e se há chaves
	 * estrangeiras nos registros de itens apontando para o tipo-item, se o
	 * tipo-item existir e não houver chaves estrangeiras efetua a exclusão
	 * 
	 * @param id
	 *            id do tipo-item a ser excluido
	 * @throws UnprocessableEntityException
	 *             disparada se houver chaves estrangeiras apontando para este
	 *             tipo-item
	 * @throws EntityNotFound
	 *             disparada se o registro a ser excluido não for encontrado
	 */
	public void deletarTipo(Long id) throws EntityNotFound, UnprocessableEntityException {
		TipoItem tipoItem = tipoItemDao.buscar(id);
		if (tipoItem == null)
			throw new EntityNotFound();
		if (tipoItem.getItensAnexados() != null)
			throw new UnprocessableEntityException();
		tipoItemDao.deletar(tipoItem);
	}
}
