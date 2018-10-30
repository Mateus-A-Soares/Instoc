package br.com.lupus.utils;

import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BindingResultUtils {

	public static HashMap<String, String> toHashMap(BindingResult br, List<String> fields){
		HashMap<String, String> hashMap = new HashMap<>();
		for(FieldError fe : br.getFieldErrors()) {
			if(fields.contains(fe.getField())) {
				hashMap.put(fe.getField(), fe.getDefaultMessage());
			}
		}
		return hashMap;
	}
	
	public static HashMap<String, String> toHashMap(BindingResult br){
		HashMap<String, String> hashMap = new HashMap<>();
		for(FieldError fe : br.getFieldErrors()) {
			hashMap.put(fe.getField(), fe.getDefaultMessage());
		}
		return hashMap;
	}
}
