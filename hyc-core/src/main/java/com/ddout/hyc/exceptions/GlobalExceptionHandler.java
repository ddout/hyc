package com.ddout.hyc.exceptions;

import com.ddout.hyc.bean.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ConditionalOnProperty(name = "hyc.core.handler.exceptionHandler.enabled", matchIfMissing = true)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = GlobalException.class)
    public ResponseData GlobalExceptionHandler(HttpServletRequest req, GlobalException e) throws GlobalException {
        logger.info(e.getMessage(), e);
        ResponseData response = new ResponseData();
        response.setCode(e.getCode());
        response.setMsg(e.getMessage());
        return response;
    }

    @ExceptionHandler()
    public ResponseData unknowExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error(e.getMessage(), e);
        ResponseData response = new ResponseData();
        response.setCode(ResponseCode.SERVER_EXCEPTION.getCode());
        response.setMsg(ResponseCode.SERVER_EXCEPTION.getMsg());
        return response;
    }
}

