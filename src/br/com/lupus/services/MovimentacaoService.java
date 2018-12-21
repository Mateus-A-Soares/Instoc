package br.com.lupus.services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.lupus.dao.AmbienteDao;
import br.com.lupus.dao.ItemDao;
import br.com.lupus.dao.MovimentacaoDao;
import br.com.lupus.exceptions.ConflictException;
import br.com.lupus.exceptions.EntityNotFoundException;
import br.com.lupus.models.Ambiente;
import br.com.lupus.models.Item;
import br.com.lupus.models.Movimentacao;
import br.com.lupus.models.Usuario;

/**
 * Classe com os métodos auxiliares referentes ao CRUD de movimentações
 * 
 * @author Mateus A.S
 */
@Service
public class MovimentacaoService {

	@Autowired
	private MovimentacaoDao movimentacaoDao;

	@Autowired
	private AmbienteDao ambienteDao;

	@Autowired
	private ItemDao itemDao;

	/**
	 * Método que persiste uma movimentação na base de dados. Recebe o id de um item
	 * e de um ambiente, se o ambiente e o item existirem, popula uma movimentação e
	 * persiste ela. Automaticamente o banco atualiza o ambiente para qual o item
	 * aponta.
	 * 
	 * @param itemId
	 *            id do item a ser movimentado
	 * @param ambienteId
	 *            id do ambiente a qual o item está sendo redirecionado
	 * @throws EntityNotFoundException
	 *             disparada se uma das entidades não for encontrada
	 * @throws ConflictException
	 *             disparada se o item estiver sendo movido para o local em que ele
	 *             já se encontra
	 */
	@Transactional(value = TxType.REQUIRED)
	public Movimentacao movimentar(Long itemId, Long ambienteId) throws EntityNotFoundException, ConflictException {
		Item item = itemDao.buscar(itemId);
		Ambiente ambiente = ambienteDao.buscar(ambienteId);
		;
		if (item == null || ambiente == null)
			throw new EntityNotFoundException();
		Hibernate.initialize(item.getAmbienteAtual());
		if (item.getAmbienteAtual().getId() == ambienteId)
			throw new ConflictException();
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setAmbienteAnterior(item.getAmbienteAtual());
		movimentacao.setAmbientePosterior(ambiente);
		movimentacao.setItemMovimentado(item);
		movimentacao.setMovimentador((Usuario) SecurityContextHolder.getContext().getAuthentication());
		movimentacao.setDataMovimentacao(new Date());
		movimentacaoDao.persistir(movimentacao);
		return movimentacao;
	}
}
