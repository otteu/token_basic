package com.delivery.delivery_app.api.exception.common;


import com.delivery.delivery_app.api.exception.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomApiException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public CustomApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if(message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s, %s", errorCode.getMessage(), message);
    }
}
