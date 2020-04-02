package com.lizhihong.basecomment.constant;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(true, 200, "success"),
    UNKNOWN_ERROR(false, 201, "unknown error"),
    PARAM_ERROR(false, 202, "param error"),
    NULL_POINTER(false, 203, "null pointer"),
    HTTP_CLIENT_ERROR(false, 204, "http client error");

    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}