package com.udacity.vehicles.api;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.udacity.vehicles.service.CarNotFoundException;

/**
 * Implements the Error controller related to any errors handled by the Vehicles API
 */
@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

	private static final String CAR_NOT_FOUND_EXCEPTION_MESSAGE = "Car Not Found Exception: ";

	Logger logger = LoggerFactory.getLogger(getClass());

	private static final String DEFAULT_VALIDATION_FAILED_MESSAGE = "Validation failed";

	@ExceptionHandler(value = {CarNotFoundException.class})
	public ResponseEntity<Object> handleCarNotFoundException(CarNotFoundException ex) {
		logger.error(CAR_NOT_FOUND_EXCEPTION_MESSAGE + ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.NOT_FOUND);
	} 
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(
						Collectors.toList());

		ApiError apiError = new ApiError(DEFAULT_VALIDATION_FAILED_MESSAGE, errors);
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}
}

