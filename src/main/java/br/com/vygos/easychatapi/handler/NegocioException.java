package br.com.vygos.easychatapi.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NegocioException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

	public NegocioException(HttpStatus status, String message) {
	    super(message);
	    this.status = status;
    }
	public NegocioException(HttpStatus status) {
        this.status = status;
    }
}
