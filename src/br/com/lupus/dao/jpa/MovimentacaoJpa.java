package br.com.lupus.dao.jpa;

import org.springframework.stereotype.Repository;

import br.com.lupus.dao.MovimentacaoDao;
import br.com.lupus.models.Movimentacao;

/**
 * 	MovimentacaoJpa que herda todos os métodos comuns aos DAO's da classe AbstractJPA
 * 
 * @author Mateus A.S
 */
@Repository("movimentacaoDao")
public class MovimentacaoJpa extends AbstractJPA<Movimentacao> implements MovimentacaoDao {

	@Override
	public String getNomeEntidade() {
		return "Movimentacao";
	}
}
