package com.ddout.hyc.exceptions;


/**
 * 业务异常
 *
 * @author ddout
 * @Copyright (C), 沪友科技
 */
public class BizException extends GlobalException {

    public BizException() {
        super(ResponseCode.BIZ_EXCEPTION.getMsg(), ResponseCode.BIZ_EXCEPTION.getCode());
    }

    public BizException(String message) {
        super(message, ResponseCode.BIZ_EXCEPTION.getCode());
    }

    public BizException(String message, int code) {
        super(message, code);
    }
}
