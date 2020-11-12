package org.softwire.training.api.models;

import lombok.Getter;

public class FailedRequestException extends RuntimeException {

    private @Getter ErrorCode errorCode;

    public FailedRequestException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FailedRequestException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
