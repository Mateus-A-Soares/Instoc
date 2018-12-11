package br.com.lupus.services;

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

import br.com.lupus.dao.AmbienteDao;
import br.com.lupus.dao.ItemDao;
import br.com.lupus.dao.TipoItemDao;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.models.Ambiente;
import br.com.lupus.models.Item;
import br.com.lupus.models.TipoItem;
import br.com.lupus.models.Usuario;

/**
 * Classe com os métodos auxiliares referentes ao CRUD de itens
 * 
 * @author Mateus A.S
 */
@Service
public class ItemService {

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private TipoItemDao tipoItemDao;

	@Autowired
	private AmbienteDao ambienteDao;

	/**
	 * Método que retorna uma lista com todos os itens cadastrados na base de dados
	 * 
	 * @return lista de itens cadastrados
	 */
	public List<Item> buscarItens() {
		return itemDao.buscarTodos();
	}

	/**
	 * Método que retorna um item cadastrado na base de dados através do seu id
	 * 
	 * @param id
	 *            id do item procurado
	 * @return objeto item populado
	 * @throws EntityNotFound
	 *             disparada quando não existe item referenciado ao id passado
	 */
	@Transactional(value = TxType.REQUIRED)
	public Item buscar(Long id) throws EntityNotFound {
		Item item = itemDao.buscar(id);
		if (item == null)
			throw new EntityNotFound();
		Hibernate.initialize(item.getCadastrante());
		Hibernate.initialize(item.getAmbienteAtual());
		return item;
	}

	/**
	 * Método que persiste um item na base de dados. Recebe um objeto item populado,
	 * verifica se há erros de validação, se não houver define o usuário logado como
	 * o cadastrante do item e efetua a persistência
	 * 
	 * @param item
	 *            objeto item populado
	 * @param brItem
	 *            objeto populado com os possíveis erros de validação
	 * @throws UnprocessableEntityException
	 *             disparada se houver erros de validação
	 */
	public void cadastrar(Item item, BindingResult brItem) throws UnprocessableEntityException {
		if (item.getAmbienteAtual().getId() == null)
			brItem.addError(new FieldError("item", "ambienteAtual", "Id do ambiente não definido"));
		if (item.getTipo().getId() == null)
			brItem.addError(new FieldError("item", "tipo", "Id do tipo não definido"));
		if (brItem.hasFieldErrors())
			throw new UnprocessableEntityException();
		Ambiente ambienteAtual = ambienteDao.buscar(item.getAmbienteAtual().getId());
		TipoItem tipoItem = tipoItemDao.buscar(item.getTipo().getId());
		if (ambienteAtual == null)
			brItem.addError(new FieldError("item", "ambienteAtual", "Ambiente não existente"));
		if (tipoItem == null)
			brItem.addError(new FieldError("item", "tipo", "Tipo não existente"));
		if (brItem.hasFieldErrors())
			throw new UnprocessableEntityException();
		item.setCadastrante((Usuario) SecurityContextHolder.getContext().getAuthentication());
		itemDao.persistir(item);
	}

	/**
	 * Método que atualiza um item cadastrado na base de dados. Recebe um objeto
	 * item populado, verifica se há erros de validação, se não houver, efetua a
	 * atualização
	 * 
	 * @param item
	 *            objeto populado com os novos valores a serem atualizados
	 * @param brItem
	 *            objeto populado com os possíveis erros de validação
	 * @return objeto item populado com o registro que foi atualizado
	 * @throws UnprocessableEntityException
	 *             disparada se houver erros de validação
	 * @throws EntityNotFound
	 *             disparada se o registro a ser editado não for encontrado
	 */
	@Transactional(value = TxType.REQUIRED)
	public Item atualizar(@Valid Item item, BindingResult brItem) throws UnprocessableEntityException, EntityNotFound {
		Item itemAntigo = itemDao.buscar(item.getId());
		if (itemAntigo == null)
			throw new EntityNotFound();
		if (item.getTipo() != null && item.getTipo().getId() != null) {
			TipoItem tipoItem = tipoItemDao.buscar(item.getTipo().getId());
			if (tipoItem == null)
				brItem.addError(new FieldError("item", "tipo", "Tipo não existente"));
			else
				itemAntigo.setTipo(tipoItem);
		}
		if (brItem.hasFieldErrors())
			throw new UnprocessableEntityException();
		itemDao.atualizar(itemAntigo);
		Hibernate.initialize(itemAntigo.getAmbienteAtual());
		return itemAntigo;
	}

	/**
	 * Método que exclui fisicamente um item cadastrado na base de dados. Verifica
	 * se existe registro de um item referente ao id passado, se o ambiente
	 * existir efetua a exclusão
	 * 
	 * @param id
	 *            id do item a ser excluido
	 * @throws EntityNotFound
	 *             disparada se o registro a ser excluido não for encontrado
	 */
	public void deletarItem(Long id) throws EntityNotFound {
		Item item = itemDao.buscar(id);
		if (item == null)
			throw new EntityNotFound();
		itemDao.deletar(item);
	}
}
