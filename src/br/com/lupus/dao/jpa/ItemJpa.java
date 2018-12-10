package br.com.lupus.dao.jpa;

import org.springframework.stereotype.Repository;

import br.com.lupus.dao.ItemDao;
import br.com.lupus.models.Item;

@Repository("itemDao")
public class ItemJpa extends AbstractJPA<Item> implements ItemDao {

	@Override
	public String getNomeEntidade() {
		
		return "Item";
	}
}
