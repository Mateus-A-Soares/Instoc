package br.com.lupus.dao.jpa;

import org.springframework.stereotype.Repository;

import br.com.lupus.dao.TipoItemDao;
import br.com.lupus.models.TipoItem;

@Repository("tipoItemDao")
public class TipoItemJpa extends AbstractJPA<TipoItem> implements TipoItemDao {

	@Override
	public String getNomeEntidade() {
		// TODO Auto-generated method stub
		return "TipoItem";
	}

}
