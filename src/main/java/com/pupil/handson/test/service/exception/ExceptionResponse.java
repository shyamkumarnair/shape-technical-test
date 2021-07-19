package com.pupil.handson.test.service.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ExceptionResponse
 * Provides more structured details about the exception 
 * 
 * @author PattathilS
 *
 */
@Data
@AllArgsConstructor
public class ExceptionResponse {
	private LocalDateTime timestamp;
	private String message;
	private String details;
}
