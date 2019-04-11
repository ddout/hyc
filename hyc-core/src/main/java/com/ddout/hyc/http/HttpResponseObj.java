package com.ddout.hyc.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;

import java.util.Map;

public class HttpResponseObj {
    /**
     * 响应码(参考http状态码)
     */
    private int statusCode;
    /**
     * 返回内容
     */
    private String content;
    /**
     * 请求headers
     */
    private transient Header[] requestHeaders;
    /**
     * 响应headers
     */
    private transient Header[] responseHeaders;
    /**
     * 请求与响应对象实体信息
     */
    private transient JSONObject entity;

    public HttpResponseObj() {
        super();
    }

    public HttpResponseObj(int statusCode, String content, Header[] requestHeaders, Header[] responseHeaders, JSONObject entity) {
        super();
        this.statusCode = statusCode;
        this.content = content;
        this.setRequestHeaders(requestHeaders);
        this.setResponseHeaders(responseHeaders);
        this.entity = entity;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String data) {
        this.content = content;
    }


    public JSONObject getEntity() {
        return entity;
    }

    public void setEntity(JSONObject entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("statusCode", statusCode);
        json.put("content", content);
        json.put("requestHeaders", getRequestHeaders());
        json.put("responseHeaders", getResponseHeaders());
        json.put("entity", getEntity());
        return json.toJSONString();
    }

    /**
     * 请求headers
     */
    public Header[] getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Header[] requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    /**
     * 响应headers
     */
    public Header[] getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
