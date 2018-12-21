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
import br.com.lupus.dao.TipoItemTagDao;
import br.com.lupus.exceptions.EntityNotFoundException;
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

	@Autowired
	private TipoItemTagDao tagDao;

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
	 * @throws EntityNotFoundException
	 *             disparada quando não existe tipo-item referenciado ao id passado
	 */
	@Transactional(value = TxType.REQUIRED)
	public TipoItem buscaTipo(Long id) throws EntityNotFoundException {
		TipoItem tipoItem = tipoItemDao.buscar(id);
		if (tipoItem == null)
			throw new EntityNotFoundException();
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
	 * @throws EntityNotFoundException
	 *             disparada se o registro a ser editado não for encontrado
	 * @throws UnprocessableEntityException
	 *             disparada se houver erros de validação
	 * 
	 */
	@Transactional(value = TxType.REQUIRED)
	public TipoItem editarTipo(@Valid TipoItem tipoItem, BindingResult brTipo)
			throws EntityNotFoundException, UnprocessableEntityException {
		TipoItem tipoItemAntigo = tipoItemDao.buscar(tipoItem.getId());
		if (tipoItemAntigo == null)
			throw new EntityNotFoundException();
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
	 * Método que persiste uma tag na base de dados. Recebe um objeto TipoItemTag
	 * populado, verifica se o TipoItem referenciado existe, e se existir verifica
	 * possíveis erros de validação, se não hover erros efetua a persistência
	 * 
	 * @param id
	 *            id do TipoItem referente a tag
	 * @param tag
	 *            objeto tag populado
	 * @param brTag
	 *            objeto populado com os possíveis erros de validação
	 * @throws EntityNotFoundException
	 *             dispara se não existir registro de TipoItem referente ao id
	 *             passado
	 * @throws UnprocessableEntityException
	 *             dispara se hover erros de validação
	 */
	public void persistirTag(Long id, TipoItemTag tag, BindingResult brTag)
			throws EntityNotFoundException, UnprocessableEntityException {
		TipoItem tipoItem = tipoItemDao.buscar(id);
		if (tipoItem == null)
			throw new EntityNotFoundException();
		if (brTag.hasFieldErrors())
			throw new UnprocessableEntityException();
		tag.setTipoItem(tipoItem);
		tagDao.persistir(tag);
	}

	/**
	 * Método que persiste uma lista de tags na base de dados. Recebe uma lista de objetos TipoItemTag
	 * populados, verifica se o TipoItem referenciado existe, se existir percorre a lista persistindo
	 * todas quais não contém erros de validação.
	 * 
	 * @param id
	 *            id do TipoItem referente a lista de tags
	 * @param tags
	 *            lista de objetos tag populados
	 * @throws EntityNotFoundException
	 *             dispara se não existir registro de TipoItem referente ao id
	 *             passado
	 */
	public void persistirTags(Long id, List<TipoItemTag> tags) throws EntityNotFoundException {
		TipoItem tipoItem = tipoItemDao.buscar(id);
		if (tipoItem == null)
			throw new EntityNotFoundException();

		for (int i = 0; i < tags.size(); i++) {
			TipoItemTag tag = tags.get(i);
			if (tag.getCabecalho() != null) {
				tag.setTipoItem(tipoItem);
				tagDao.persistir(tag);
			}
		}
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
	 * @throws EntityNotFoundException
	 *             disparada se o registro a ser excluido não for encontrado
	 */
	public void deletarTipo(Long id) throws EntityNotFoundException, UnprocessableEntityException {
		TipoItem tipoItem = tipoItemDao.buscar(id);
		if (tipoItem == null)
			throw new EntityNotFoundException();
		if (tipoItem.getItensAnexados() != null)
			throw new UnprocessableEntityException();
		tipoItemDao.deletar(tipoItem);
	}
}
