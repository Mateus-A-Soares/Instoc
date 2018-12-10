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

@Service
public class ItemService {

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private TipoItemDao tipoItemDao;

	@Autowired
	private AmbienteDao ambienteDao;

	public List<Item> buscarItens() {
		return itemDao.buscarTodos();
	}

	@Transactional(value = TxType.REQUIRED)
	public Item buscar(Long id) throws EntityNotFound {
		Item item = itemDao.buscar(id);
		if (item == null)
			throw new EntityNotFound();
		Hibernate.initialize(item.getCadastrante());
		Hibernate.initialize(item.getAmbienteAtual());
		return item;
	}

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

	@Transactional(value = TxType.REQUIRED)
	public Item atualizar(@Valid Item item, BindingResult brItem) throws UnprocessableEntityException, EntityNotFound {
		Item itemAntigo = itemDao.buscar(item.getId());
		if (itemAntigo == null)
			throw new EntityNotFound();
		if (item.getTipo() != null && item.getTipo().getId() != null) {
			TipoItem tipoItem = tipoItemDao.buscar(item.getTipo().getId());
			if(tipoItem == null)
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

	public void deletarItem(Long id) throws EntityNotFound {
		Item item = itemDao.buscar(id);
		if (item == null)
			throw new EntityNotFound();
		itemDao.deletar(item);
	}
}
