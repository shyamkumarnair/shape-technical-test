package com.pupil.handson.test.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InvalidSquareException
 * 
 * @author PattathilS
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidSquareException extends RuntimeException {

	private static final long serialVersionUID = 2122111424622787756L;

	public InvalidSquareException(String errorMessage) {
		super(errorMessage);
	}

	public InvalidSquareException() {
		super("Invalid Square Exception");
	}
}
