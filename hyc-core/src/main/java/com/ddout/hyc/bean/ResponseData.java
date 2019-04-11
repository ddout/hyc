package com.ddout.hyc.bean;


import com.alibaba.fastjson.JSONObject;
import com.ddout.hyc.exceptions.ResponseCode;
import com.ddout.hyc.sleuth.SessionPropagationBean;
import com.ddout.hyc.text.Base64Util;

/**
 * 响应数据对象
 * rows=响应的数据对象（json字符串base64编码字串）
 */
public class ResponseData implements java.io.Serializable {
    /**
     * 日志追踪TraceId
     */
    private String traceId = SessionPropagationBean.getTraceId();
    /**
     * 日志追踪SpanId
     */
    private String spanId = SessionPropagationBean.getSpanId();
    /**
     * 日志追踪ParentId
     */
    private String parentId = SessionPropagationBean.getParentId();
    /**
     * 返回状态码
     */
    private int code = ResponseCode.SUCCESS.getCode();
    /**
     * 返回说明
     */
    private String msg = ResponseCode.SUCCESS.getMsg();
    /**
     * 返回的数据（json字符串base64编码字串）
     */
    private String rows = "";

    public static JSONObject parseRows(String rows){
        if(!"".equals(rows)){
            return JSONObject.parseObject(Base64Util.decode(rows));
        }
        return new JSONObject();
    }


    public ResponseData() {
        super();
    }

    public ResponseData(String rows) {
        this.setRows(rows);
    }

    public static ResponseData success() {
        return new ResponseData();
    }

    public static ResponseData success(Object rows) {
        return new ResponseData(JSONObject.toJSONString(rows));
    }

    /**
     * 日志追踪TraceId
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * 日志追踪SpanId
     */
    public String getSpanId() {
        return spanId;
    }

    /**
     * 日志追踪ParentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 返回状态码
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 返回说明
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 返回的数据（json字符串base64编码字串）
     */
    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = Base64Util.encode(rows);
    }

    public void setRows(JSONObject rows) {
        this.rows = Base64Util.encode(rows.toJSONString());
    }
}
