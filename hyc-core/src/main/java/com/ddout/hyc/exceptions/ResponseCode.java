package com.ddout.hyc.exceptions;


import com.cdhy.hyc.exceptions.*;

public enum ResponseCode {
    /**
     * SUCCESS(0, "成功")<br/>
     * BIZ_EXCEPTION(1, "业务异常")<br/>
     * DB_EXCEPTION(2, "系统DB异常")<br/>
     * HTTPTYPE_EXCEPTION(3, "请求类型不正确")<br/>
     * NOTREADABLE_EXCEPTION(4, "参数无法解析")<br/>
     * NETWORK_EXCEPTION(5, "远程调用失败"),<br/>
     * SERVER_EXCEPTION(6, "服务器目前无法工作"),<br/>
     * UNKNOW_EXCEPTION(999, "未知的错误")<br/>
     */
    SUCCESS(0, "成功",null),
    BIZ_EXCEPTION(1, "业务异常", BizException.class),
    DB_EXCEPTION(2, "系统DB异常", ServerException.class),
    HTTPTYPE_EXCEPTION(3, "请求类型不正确", ParamException.class),
    NOTREADABLE_EXCEPTION(4, "参数无法解析", ParamException.class),
    NETWORK_EXCEPTION(5, "远程调用失败", RemoteNetworkException.class),
    SERVER_EXCEPTION(6, "服务器目前无法工作", ServerException.class),
    UNKNOW_EXCEPTION(999, "未知的错误", GlobalException.class);

    private int code;
    private String msg;
    private Class cls;

    private ResponseCode(int code, String msg, Class cls) {
        this.msg = msg;
        this.code = code;
        this.cls = cls;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    public Class getCls(){return cls;}
}
