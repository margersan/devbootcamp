package com.exactorysolution.devbootcamp.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.exactorysolution.devbootcamp.services.exceptions.DatabaseException;
import com.exactorysolution.devbootcamp.services.exceptions.ResourceNotFoundException;

@ControllerAdvice //permite que a classe intercepte alguma excessão
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class) //para que o controlador saiba qual exceção ele deve interceptar
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status).body(FillVariables(e.getMessage(), request.getRequestURI(), status));
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> entityNotFound(DatabaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(FillVariables(e.getMessage(), request.getRequestURI(), status));
	}
	
	private StandardError FillVariables(String msg, String getUri, HttpStatus status) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found!");
		err.setMessage(msg); //mensagem do orElseThrow
		err.setPath(getUri); //busca o caminho da requisição
		return err;
	}
}
