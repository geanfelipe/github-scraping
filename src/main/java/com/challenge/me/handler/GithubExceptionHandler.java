
package com.challenge.me.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
		exception.printStackTrace();
		final ErrorResponseDto errorResponse =
				new ErrorResponseDto(exception.getStatusCode().value(), exception.getLocalizedMessage());
		return new ResponseEntity<>(errorResponse, exception.getStatusCode());
	}
}
