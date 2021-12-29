package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public Map<String, String> errors = new HashMap<>();
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErros(){
		return errors;
	}
	
	public void addErrors(String fieldNome, String errorMessage) {
		errors.put(fieldNome, errorMessage);
	}
	
}
