package br.com.texsistemas.financemanager.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        this(message, (String) null);
    }

    public BusinessException(String message, Throwable cause) {
        this(message, cause, null);
    }
}