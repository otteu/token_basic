package com.delivery.delivery_app.api.exception.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // jwt
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),

    // Account Error
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
    DUPLICATED_ACCOUNT_NICKNAME(HttpStatus.CONFLICT, "Account nickname duplicate"),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "Accont not found"),

    // Post Error
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not found"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),

    // Like
    ALREADY_LIKED(HttpStatus.CHECKPOINT, "Account aleady liked"),

    // Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")
    ;


    private final HttpStatus status;
    private final String message;
}
