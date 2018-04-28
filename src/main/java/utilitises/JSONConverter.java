package utilitises;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {

	private static final ObjectMapper mapper = new ObjectMapper(); 
	
	public static String object2JSON(Object o) throws JsonProcessingException {
		String res= mapper.writeValueAsString(o);
		return res;
	}
	
	public static ObjectMapper getMapper() {
		return mapper;
	}
	
}
