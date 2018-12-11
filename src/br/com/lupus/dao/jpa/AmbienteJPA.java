package br.com.lupus.dao.jpa;

import org.springframework.stereotype.Repository;

import br.com.lupus.dao.AmbienteDao;
import br.com.lupus.models.Ambiente;

/**
 * 	AmbienteJPA que herda todos os m�todos comuns aos DAO's da classe AbstractJPA
 * 
 * @author Mateus A.S
 */
@Repository("ambienteDao")
public class AmbienteJPA extends AbstractJPA<Ambiente> implements AmbienteDao{

	@Override
	public String getNomeEntidade() {
		return "Ambiente";
	}
}
