package com.ddout.hyc.bean;


import com.alibaba.fastjson.JSONObject;
import com.ddout.hyc.collections.MapUtil;
import com.ddout.hyc.text.Base64Util;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求数据对象
 * param=请求的数据对象（json字符串base64编码字串）
 */
public class RequestData implements Serializable {
    /**
     * 请求的数据JSON字串Base64
     */
    private String param;

    private JSONObject paramJson;

    public RequestData() {
        super();
    }

    /**
     * 构建一个新的框架规范-请求数据
     *
     * @param data 实际业务数据
     * @return
     */
    public static JSONObject buildNewRequest(Object data) {
        JSONObject req = new JSONObject();
        req.put("param", Base64Util.encode(JSONObject.toJSONString(data)));
        return req;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
        String decode = Base64Util.decode(this.param);
        this.paramJson = JSONObject.parseObject(decode);
    }

    /**
     * 获取请求数据的JSON对象
     *
     * @return JSONObject
     */
    public JSONObject getParamJson() {
        return paramJson;
    }

    /**
     * 获取数据：
     *
     * @param key
     * @return 如果key不存在，或数据本为空，则返回null
     */
    public Object getJSONVal(String key) {
        if (null != this.paramJson && this.paramJson.containsKey(key)) {
            return this.paramJson.get(key);
        } else {
            return null;
        }
    }

    /**
     * 获取数据：字符串，默认值为空串""
     *
     * @param key
     * @return
     */
    public String getStringVal(String key) {
        return MapUtil.getString(this.paramJson, key, "");
    }

    /**
     * 获取数据：字符串
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public String getStringVal(String key, String defaultVal) {
        return MapUtil.getString(this.paramJson, key, defaultVal);
    }

    /**
     * 获取数据：无空白字符的字符串，默认值为空串""
     *
     * @param key
     * @return
     */
    public String getStringValNotBlank(String key) {
        return MapUtil.getStringNoBlank(this.paramJson, key, "");
    }

    /**
     * 获取数据：无空白字符的字符串
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public String getStringValNotBlank(String key, String defaultVal) {
        return MapUtil.getStringNoBlank(this.paramJson, key, defaultVal);
    }

    /**
     * 获取数据：int型，默认值0
     *
     * @param key
     * @return
     */
    public int getIntVal(String key) {
        return MapUtil.getIntVal(this.paramJson, key, 0);
    }

    /**
     * 获取数据：int型
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public int getIntVal(String key, int defaultVal) {
        return MapUtil.getIntVal(this.paramJson, key, defaultVal);
    }

    /**
     * 获取数据：Integer型,默认值null
     *
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        return MapUtil.getInt(this.paramJson, key);
    }

    /**
     * 获取数据：long型,默认值0
     *
     * @param key
     * @return
     */
    public long getLongVal(String key) {
        return MapUtil.getLongVal(this.paramJson, key, 0);
    }

    /**
     * 获取数据：long型
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public long getLongVal(String key, long defaultVal) {
        return MapUtil.getLongVal(this.paramJson, key, defaultVal);
    }

    /**
     * 获取数据：Long型,默认值null
     *
     * @param key
     * @return
     */
    public Long getLong(String key) {
        return MapUtil.getLongVal(this.paramJson, key);
    }

    /**
     * 获取数据：double型,默认值0.0
     *
     * @param key
     * @return
     */
    public double getDoubleVal(String key) {
        return MapUtil.getDoubleVal(this.paramJson, key, 0.0);
    }

    /**
     * 获取数据：double型
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public double getDoubleVal(String key, double defaultVal) {
        return MapUtil.getDoubleVal(this.paramJson, key, defaultVal);
    }

    /**
     * 获取数据：Double型,默认值null
     *
     * @param key
     * @return
     */
    public Double getDouble(String key) {
        return MapUtil.getDouble(this.paramJson, key);
    }

    /**
     * 获取数据：Date,默认值null
     *
     * @param key
     * @return
     */
    public Date getDate(String key) {
        return MapUtil.getDate(this.paramJson, key);
    }

    /**
     * 获取数据：Date,默认值null
     *
     * @param key
     * @param fmt
     * @return
     */
    public Date getDate(String key, String fmt) {
        return MapUtil.getDate(this.paramJson, key, fmt);
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("param", this.param);
        json.put("paramJson", this.paramJson);
        return json.toString();
    }

    public JSONObject toRequestJSON() {
        JSONObject json = new JSONObject();
        json.put("param", Base64Util.encode(this.paramJson.toJSONString()));
        return json;
    }

    public JSONObject toRequestJSON(JSONObject parm) {
        JSONObject json = new JSONObject();
        JSONObject req = JSONObject.parseObject(this.paramJson.toJSONString());
        req.putAll(parm);
        json.put("param", Base64Util.encode(req.toJSONString()));
        return json;
    }

    public String toRequestString() {
        return toRequestJSON().toString();
    }

    public String toRequestString(JSONObject parm) {
        return toRequestJSON(parm).toString();
    }

}
