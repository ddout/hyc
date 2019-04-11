package com.ddout.hyc;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSONObject;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.http.HttpClientApi;
import com.ddout.hyc.http.HttpResponseObj;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.util.HashMap;

/**
 * Unit test for simple EiAccessApplication.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws InterruptedException {

        String url = "http://127.0.0.1:8600/ei-access/test";
        JSONObject json = new JSONObject();
        json.put("a", "1");
        json.put("b", "12.232323141414");
        json.put("c", "你好");
        json.put("pk", 1222);

        HttpResponseObj res = HttpClientApi.doPost(url, RequestData.buildNewRequest(json).toJSONString(), HttpClientApi.CHARSET_UTF8, new HashMap<String, String>() {{
            put("Content-Type", ContentType.APPLICATION_JSON.toString());
            put("x-hyc-user-id", "user0000001");
            put("x-hyc-session-id", "session000009999999");

        }});
        System.out.println(res.getContent());


    }
}
