package com.ddout.hyc.exceptions;

/**
 * 服务器故障类
 */
public class ServerException extends GlobalException {
    public ServerException() {
        super(ResponseCode.SERVER_EXCEPTION.getMsg(), ResponseCode.SERVER_EXCEPTION.getCode());
    }

    public ServerException(String message) {
        super(message, ResponseCode.SERVER_EXCEPTION.getCode());
    }

    public ServerException(String message, int code) {
        super(message, code);
    }
}
