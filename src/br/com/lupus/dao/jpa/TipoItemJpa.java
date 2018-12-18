package br.com.lupus.dao.jpa;

import org.springframework.stereotype.Repository;

import br.com.lupus.dao.TipoItemDao;
import br.com.lupus.models.TipoItem;

/**
 * 	TipoItemJPA que herda todos os métodos comuns aos DAO's da classe AbstractJPA
 * 
 * @author Mateus A.S
 */
@Repository("tipoItemDao")
public class TipoItemJpa extends AbstractJPA<TipoItem> implements TipoItemDao {

	@Override
	public String getNomeEntidade() {
		return "TipoItem";
	}

}
