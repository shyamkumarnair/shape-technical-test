package com.pupil.handson.test.service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller Advice Handles all exception is Shape application centrally
 * 
 * @author PattathilS
 *
 */

@RestControllerAdvice
public class ShapeExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleExceptions(Exception e, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), e.getMessage(),
				request.getDescription(false));
		logger.error("Exception happened - " + exceptionResponse);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidSquareException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleSquareInvalidException(InvalidSquareException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.error("Square shape invalid Exception " + exceptionResponse);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SquareOverlapException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleSquareOverlapException(SquareOverlapException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.error("Square overlaps with existing square shapes exception " + exceptionResponse);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), "Failed Validation",
				ex.getBindingResult().toString());
		logger.error("Validation Failed Exception  " + exceptionResponse);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
