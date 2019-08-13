package com.ddout.hyc.http;

import com.alibaba.fastjson.JSONObject;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.exceptions.ResponseCode;
import com.ddout.hyc.bean.ResponseData;
import com.ddout.hyc.exceptions.GlobalException;
import com.ddout.hyc.exceptions.RemoteNetworkException;
import com.ddout.hyc.sleuth.SessionPropagationBean;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 用于框架内组件Rest接口通讯工具
 */
@Component
@ConfigurationProperties(prefix = "hyc")
public class RestServiceAPI {

    @Value("${hyc.gateway.ip}")
    private String gatewayIP;
    @Value("${hyc.gateway.port}")
    private int gatewayPort;

    @Autowired
    private SessionPropagationBean sessionPropagationBean;

    public ResponseData get(String url, String parms) {
        return null;
    }

    /**
     * 发送post请求(附带追踪信息)
     *
     * @param uri 请求路径
     * @param jsonData 请求数据
     * @return
     */
    public JSONObject rest(String uri, JSONObject jsonData) {
        String url = "http://" + gatewayIP + ":" + gatewayPort + "/" + uri;
        return post(url, jsonData);
    }
    /**
     * 发送post请求(附带追踪信息)
     *
     * @param url      请求路径
     * @param jsonData 请求数据
     * @return
     */
    private JSONObject post(String url, JSONObject jsonData) {
        //
        Map<String, String> headers = sessionPropagationBean.getAll();
        headers.put("Content-Type", ContentType.APPLICATION_JSON.toString());
        HttpResponseObj httpResponseObj = HttpClientApi.doPost(url, RequestData.buildNewRequest(jsonData).toJSONString(),
                HttpClientApi.CHARSET_UTF8, headers);
        //
        return parseResponse(httpResponseObj);
        //
    }

    private JSONObject parseResponse(HttpResponseObj httpResponseObj) {
        int statusCode = httpResponseObj.getStatusCode();
        if (HttpStatus.SC_OK == statusCode) {
            //虽然是200，但是response-code不为0，也就是说这里的远程方没有处理成功；需要继续抛出异常
            ResponseData responseData;
            try {
                JSONObject json = JSONObject.parseObject(httpResponseObj.getContent());
                responseData = new ResponseData();
                responseData.setCode(json.getInteger("code"));
                responseData.setMsg(json.getString("msg"));
                responseData.setRows(ResponseData.parseRows(json.getString("rows")));
            } catch (Exception e) {
                throw new GlobalException(ResponseCode.NOTREADABLE_EXCEPTION.getMsg(), ResponseCode.NOTREADABLE_EXCEPTION.getCode(), e);
            }
            if (null == responseData) {
                throw new GlobalException(ResponseCode.NOTREADABLE_EXCEPTION.getMsg(), ResponseCode.NOTREADABLE_EXCEPTION.getCode());
            }
            //
            int responseCode = responseData.getCode();
            if (ResponseCode.SUCCESS.getCode() == responseCode) {
                //成功的处理
                return ResponseData.parseRows(responseData.getRows());
            } else {
                for (ResponseCode rc : ResponseCode.values()) {
                    if (rc.getCode() == responseCode) {
                        try {
                            Constructor constructor = rc.getCls().getConstructor(new Class[]{String.class, int.class});
                            GlobalException e = ((GlobalException) constructor.newInstance(new Object[]{responseData.getMsg(), responseCode}));
                            throw e;
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            //其他情况统一给出未知的异常
            throw new GlobalException(responseData.getMsg(), responseCode);
        } else {
            /**
             * 需要处理http的状态码--原因：地址无效404，对方500，415，301，403等无法访问的情况
             */
            throw new RemoteNetworkException(httpResponseObj.getContent(), httpResponseObj.getStatusCode());
        }
    }
}
