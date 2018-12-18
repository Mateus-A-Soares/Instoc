package br.com.lupus.dao.jpa;

import org.springframework.stereotype.Repository;

import br.com.lupus.dao.ItemDao;
import br.com.lupus.models.Item;

/**
 * 	ItemJpa que herda todos os m�todos comuns aos DAO's da classe AbstractJPA
 * 
 * @author Mateus A.S
 */
@Repository("itemDao")
public class ItemJpa extends AbstractJPA<Item> implements ItemDao {

	@Override
	public String getNomeEntidade() {
		
		return "Item";
	}
}
