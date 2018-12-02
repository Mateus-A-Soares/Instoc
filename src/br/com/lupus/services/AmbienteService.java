package br.com.lupus.services;

import java.util.List;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.com.lupus.dao.AmbienteDao;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.exceptions.UnprocessableEntityException;
import br.com.lupus.models.Ambiente;
import br.com.lupus.models.Usuario;

/**
 * Classe com os m�todos auxiliares referentes ao CRUD de ambientes
 * 
 * @author Mateus A.S
 */
@Service
@Transactional(value = TxType.REQUIRED)
public class AmbienteService {

	@Autowired
	private AmbienteDao ambienteDao;

	/**
	 * M�todo que retorna uma lista com todos os ambientes cadastrados na base de
	 * dados
	 * 
	 * @return lista de ambientes cadastrados
	 */
	public List<Ambiente> listarAmbientes() {
		Ambiente.setParametros(new Ambiente(), "id", "descricao");
		return ambienteDao.buscarTodos();
	}

	/**
	 * M�todo que retorna um ambiente cadastrado na base de dados atrav�s do seu id
	 * 
	 * @param id
	 *            id do ambiente procurado
	 * @return objeto ambiente populado
	 * @throws EntityNotFound
	 *             disparada quando n�o existe ambiente referenciado ao id passado
	 */
	public Ambiente pegarAmbiente(Long id) throws EntityNotFound {
		Ambiente ambiente = ambienteDao.buscar(id);
		if (ambiente == null)
			throw new EntityNotFound();
		Hibernate.initialize(ambiente.getItens()); // Popula os itens antes de retornar o ambiente
		return ambiente;
	}

	/**
	 * M�todo que persiste um ambiente na base de dados. Recebe um objeto ambiente
	 * populado, verifica se h� erros de valida��o, se n�o houver define o usu�rio
	 * logado como o cadastrante do ambiente e efetua a persist�ncia
	 * 
	 * @param ambiente
	 *            objeto ambiente populado
	 * @param brAmbiente
	 *            objeto populado com os poss�veis erros de valida��o
	 * @throws UnprocessableEntityException
	 *             disparada se houver erros de valida��o
	 */
	public void cadastrarAmbiente(Ambiente ambiente, BindingResult brAmbiente) throws UnprocessableEntityException {
		if (ambiente.getDescricao().isEmpty())
			brAmbiente.addError(new FieldError("ambiente", "descricao", "A descri��o n�o pode estar vazia"));
		if (brAmbiente.hasFieldErrors())
			throw new UnprocessableEntityException();
		ambiente.setCadastrante((Usuario) SecurityContextHolder.getContext().getAuthentication());
		ambienteDao.inserir(ambiente);
	}

	/**
	 * M�todo que atualiza um ambiente cadastrado na base de dados. Recebe um objeto
	 * ambiente populado, verifica se h� erros de valida��o, se n�o houver, efetua a
	 * atualiza��o
	 * 
	 * @param ambiente
	 *            objeto populado com os novos valores a serem atualizados
	 * @param brAmbiente
	 *            objeto populado com os poss�veis erros de valida��o
	 * @return objeto ambiente populado com o registro que foi atualizado
	 * @throws UnprocessableEntityException
	 *             disparada se houver erros de valida��o
	 * @throws EntityNotFound 
	 */
	public Ambiente editar(Ambiente ambiente, BindingResult brAmbiente) throws UnprocessableEntityException, EntityNotFound {
		Ambiente ambienteAntigo = ambienteDao.buscar(ambiente.getId());
		if(ambienteAntigo == null)
			throw new EntityNotFound();
		if (ambiente.getDescricao().isEmpty())
			ambiente.setDescricao(ambienteAntigo.getDescricao());
		if (ambiente.getCadastrante() == null)
			ambiente.setCadastrante(ambienteAntigo.getCadastrante());
		if (brAmbiente.hasFieldErrors())
			throw new UnprocessableEntityException();
		ambienteDao.alterar(ambiente);
		return ambiente;
	}

	/**
	 * M�todo que exclui fisicamente um ambiente cadastrado na base de dados.
	 * Verifica se existe ambiente referente ao id passado e se h� chaves
	 * estrangeiras nos registros de itens apontando para o ambiente, se o ambiente
	 * existir e n�o houver chaves estrangeiras efetua a exclus�o
	 * 
	 * @param id
	 *            id do item a ser excluido
	 * @throws UnprocessableEntityException
	 *             disparada se houver chaves estrangeiras apontando para este
	 *             ambiente
	 * @throws EntityNotFound
	 *             disparada se n�o existir ambiente referente ao id passado
	 */
	public void deletarAmbiente(Long id) throws UnprocessableEntityException, EntityNotFound {
		Ambiente ambiente = ambienteDao.buscar(id);
		if (ambiente == null)
			throw new EntityNotFound();
		if (!ambiente.getItens().isEmpty())
			throw new UnprocessableEntityException();
		ambienteDao.deletar(id);
	}
}
