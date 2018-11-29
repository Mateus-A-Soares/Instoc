package br.com.lupus.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lupus.dao.AmbienteDao;
import br.com.lupus.models.Ambiente;

/**
 * 	Classe com os métodos auxiliares referentes ao CRUD de ambientes
 * 
 * @author Mateus A.S
 */
@Service
public class AmbienteService {

	@Autowired
	private AmbienteDao ambienteDao;
	
	/**
	 * 	Método que retorna uma lista com todos os ambientes cadastrados
	 * 
	 * @return lista de ambientes cadastrados
	 */
	public List<Ambiente> listarAmbientes(){
		return ambienteDao.buscarTodos();
	}
}
