package com.ddout.hyc.exceptions;

public class GlobalException extends RuntimeException {
    //
    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, int code) {
        super(message);
        this.code = code;
    }
    public GlobalException(String message, int code, Throwable e) {
        super(message, e);
        this.code = code;
    }
}
