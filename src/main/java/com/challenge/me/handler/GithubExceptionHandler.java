
package com.challenge.me.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.challenge.me.dto.ErrorResponseDto;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GithubExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(HttpClientErrorException.class)
	private ResponseEntity<ErrorResponseDto> handleNotFoundException(final HttpClientErrorException exception) {
		final ErrorResponseDto errorResponse = new ErrorResponseDto(404, exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
