package br.com.lupus.utils;

import java.util.HashMap;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 	Classe auxiliar com métodos que passam os erros de um objeto BindingResult para um HashMap 
 * 
 * @author Mateus A.S
 */
public class BindingResultUtils {

	/**
	 * 	Passa os erros de campo (fieldErros) para um HashMap se esse campo estiver
	 * presente na lista de campos a ser avaliado
	 * 
	 * @param br BindingResult de referência
	 * @param fields campos avaliados
	 * @return HashMap contendo os erros dos campos presentes na lista de campos
	 */
	public static HashMap<String, String> toHashMap(BindingResult br, List<String> fields){
		HashMap<String, String> hashMap = new HashMap<>();
		for(FieldError fe : br.getFieldErrors()) {
			if(fields.contains(fe.getField())) {
				hashMap.put(fe.getField(), fe.getDefaultMessage());
			}
		}
		return hashMap;
	}
	
	/**
	 * 	Passa todos os erros de campos de um BindingResult para um HashMap
	 * 
	 * @param br BindingResult de referência
	 * @return HashMap contendo os erros dos campos de um BindingResult
	 */
	public static HashMap<String, String> toHashMap(BindingResult br){
		HashMap<String, String> hashMap = new HashMap<>();
		for(FieldError fe : br.getFieldErrors()) {
			hashMap.put(fe.getField(), fe.getDefaultMessage());
		}
		return hashMap;
	}
}
