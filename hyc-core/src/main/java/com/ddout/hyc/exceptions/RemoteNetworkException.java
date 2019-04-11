package com.ddout.hyc.exceptions;

/**
 * 远程连接异常
 */
public class RemoteNetworkException extends GlobalException {
    public RemoteNetworkException() {
        super(ResponseCode.NETWORK_EXCEPTION.getMsg(), ResponseCode.NETWORK_EXCEPTION.getCode());
    }

    public RemoteNetworkException(String message) {
        super(message, ResponseCode.NETWORK_EXCEPTION.getCode());
    }

    public RemoteNetworkException(String message, int code) {
        super(message, code);
    }
}
