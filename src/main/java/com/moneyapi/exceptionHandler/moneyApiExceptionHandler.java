package com.moneyapi.exceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class moneyApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// This message is get in file "messages.properties"
		String userMessage = messageSource.getMessage("message.fail", null, LocaleContextHolder.getLocale());

		// This message return a log more specific to developer
		String devMessage = ex.getCause().toString();

		// And finally, both userMessage and devMessage are returned in the response (in
		// JSON format) with a HTTP status code of BAD_REQUEST,
		// as required by the REST architectural style. This ensures that both the user
		// and
		// the developer receive meaningful error messages, facilitating debugging and
		// providing a better experience for the end user.
		return handleExceptionInternal(ex, new Error(userMessage, devMessage), headers, HttpStatus.BAD_REQUEST,
				request);
	}

	/**
	 * Handles the exception that occurs when method arguments fail validation.
	 * @param ex The MethodArgumentNotValidException that was thrown.
	 * @param headers The HttpHeaders to be included in the response.
	 * @param status The HttpStatusCode to be set in the response.
	 * @param request The WebRequest object representing the current request.
	 * @return A ResponseEntity containing the list of Error objects and the specified status code.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {

	    // Checks if there is more than one error and groups them in a list
	    List<Error> errors = createErrorList(ex.getBindingResult());

	    // Handles the exception internally, returning the list of errors and the specified status code
	    return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<Error> createErrorList(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<>();

		// Iterates over the FieldErrors present in the BindingResult
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			
			// Retrieves the localized message for the user
			String objectNameAndField = fieldError.getObjectName().concat(".").concat(fieldError.getField());
			
			// field format by Validation.properties
			String fieldByProperties = messageSource.getMessage(objectNameAndField,null, LocaleContextHolder.getLocale());
			
			// Context error example: Required field, field null
			String contextError = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			
			// field + Context error = Message format to User
			String userMessage = fieldByProperties.concat(" ").concat(contextError);
			
			// Retrieves the default message for the developer
			String devMessage = fieldError.toString();

			// Creates a new Error object and adds it to the list of errors
			errors.add(new Error(userMessage, devMessage));
		}

		return errors;
	}

	// Class used to join userMessage and DevMessage :)
	public static class Error {

		private String userMessage;
		private String devMessage;

		public Error(String userMessage, String devMessage) {

			this.userMessage = userMessage;
			this.devMessage = devMessage;
		}

		public String getUserMessage() {
			return userMessage;
		}

		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}

		public String getDevMessage() {
			return devMessage;
		}

		public void setDevMessage(String devMessage) {
			this.devMessage = devMessage;
		}

	}
}
