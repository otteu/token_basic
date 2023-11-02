package com.delivery.delivery_app.api.controller.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse <T> {

    private String resultCode;
    private T result;

    public static ApiResponse<Void> error(String errorCode) {
        return new ApiResponse<>(errorCode, null);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<Void>("SUCCESS", null);
    }
    public static <T>ApiResponse<T> success(T result) {
        return new ApiResponse<>("SUCCESS", result);
    }

    public String toStream() {
        if (result == null) {
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"result\":" + null +
                    "}";
        }
        return "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"," +
                "}";
    }
}
