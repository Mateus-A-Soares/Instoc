package br.com.lupus.dao.jpa;

import org.springframework.stereotype.Repository;

import br.com.lupus.dao.TipoItemTagDao;
import br.com.lupus.models.TipoItemTag;

/**
 * 	TipoItemTagJPA que herda todos os métodos comuns aos DAO's da classe AbstractJPA
 * 
 * @author Mateus A.S
 */
@Repository("tipoItemTagDao")
public class TipoItemTagJpa extends AbstractJPA<TipoItemTag> implements TipoItemTagDao {

	@Override
	public String getNomeEntidade() {
		return "tipoItemTag";
	}
}
