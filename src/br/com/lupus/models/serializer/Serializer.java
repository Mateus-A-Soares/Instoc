package br.com.lupus.models.serializer;

import java.io.IOException;import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class Serializer extends JsonSerializer<Model> {

	@Override
	public void serialize(Model model, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeStartObject();
		String[] parametros = model.getParametros();
		for (int i = 0; i < parametros.length; i++) {
			String parametro = parametros[i];
			try{				
				String nomeMetodo = "get" + parametro.substring(0, 1).toUpperCase().concat(parametro.substring(1));
				Object valor = model.getClass().getMethod(nomeMetodo).invoke(model);
				jsonGenerator.writeObjectField(parametro, valor);
			}catch(Exception e) {
				System.err.println("Não foi possível serializar o campo " + parametro + " a" + e.getMessage());
				e.printStackTrace();
			}
		}

		jsonGenerator.writeEndObject();
	}
}
