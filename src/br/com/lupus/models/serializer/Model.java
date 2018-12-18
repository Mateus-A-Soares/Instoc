package br.com.lupus.models.serializer;

import java.util.HashMap;

public abstract class Model {
	private static HashMap<String, String[]> parametros = new HashMap<>();
	
	public String getClassName() {
		return this.getClass().getName();
	};
	
	public static void setParametros(Model model, String...listaParametros) {
		parametros.put(model.getClassName(), listaParametros);
	}
	
	public String[] getParametros() {
		
		return parametros.get(getClassName());
	}
}
