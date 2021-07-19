package com.pupil.handson.test.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * SquareOverlapException
 * 
 * @author PattathilS
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SquareOverlapException extends RuntimeException {

	private static final long serialVersionUID = 2122111424622787756L;

	public SquareOverlapException(String errorMessage) {
		super(errorMessage);
	}
	
	public SquareOverlapException() {
		super("Square Overlap Exception");
	}

}
