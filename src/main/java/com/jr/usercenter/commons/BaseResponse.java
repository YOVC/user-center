package com.jr.usercenter.commons;

import lombok.Data;

/**
 *通用返回类
 */
@Data
public class BaseResponse<T> {
    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String description) {
        this(code,data,description,"");
    }

    public BaseResponse(int code, T data) {
       this(code,data,"","");
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }
}
