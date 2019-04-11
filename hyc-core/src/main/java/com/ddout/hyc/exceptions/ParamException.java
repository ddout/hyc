package com.ddout.hyc.exceptions;

/**
 * 参数异常类
 */
public class ParamException extends GlobalException {

    public ParamException() {
        super(ResponseCode.NOTREADABLE_EXCEPTION.getMsg(), ResponseCode.NOTREADABLE_EXCEPTION.getCode());
    }

    public ParamException(String message) {
        super(message, ResponseCode.NOTREADABLE_EXCEPTION.getCode());
    }

    public ParamException(String message, int code) {
        super(message, code);
    }
}
