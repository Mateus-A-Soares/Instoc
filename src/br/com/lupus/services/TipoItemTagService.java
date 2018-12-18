package br.com.lupus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lupus.dao.TipoItemTagDao;
import br.com.lupus.exceptions.EntityNotFound;
import br.com.lupus.models.TipoItemTag;

/**
 * Classe com os m�todos auxiliares referentes ao CRUD de tags
 * 
 * @author Mateus A.S
 */
@Service
public class TipoItemTagService {

	@Autowired
	private TipoItemTagDao tipoItemTagDao;

	/**
	 * M�todo que exclui fisicamente uma tag cadastrada na base de dados. Verifica
	 * se a tag referente ao id passado existe, se sim efetua a exclus�o
	 * 
	 * @param id
	 *            id da tag a ser excluida
	 * @throws EntityNotFound disparada se n�o existir tag referente ao id passado
	 */
	public void deletar(Long id) throws EntityNotFound {
		TipoItemTag tag = tipoItemTagDao.buscar(id);
		if (tag == null)
			throw new EntityNotFound();
		tipoItemTagDao.deletar(tag);
	}

}
