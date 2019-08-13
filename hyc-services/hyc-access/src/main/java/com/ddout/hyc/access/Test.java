package com.ddout.hyc.access;

import com.alibaba.fastjson.JSONObject;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.bean.ResponseData;
import com.ddout.hyc.http.RestServiceAPI;
import com.ddout.hyc.sleuth.SessionPropagationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    public static Logger logger = LoggerFactory.getLogger(Test.class);
    @Autowired
    private SessionPropagationBean sessionPropagationBean;
    @Autowired
    private RestServiceAPI restServiceAPI;

    @RequestMapping(value = "test")
    public ResponseData test(@RequestBody RequestData request){
        logger.debug(JSONObject.toJSONString(sessionPropagationBean.getAll()));
        //
        logger.debug(request.toString());

        String url2 = "http://127.0.0.1:8600/ei-site-buss/test";
        JSONObject json2 = new JSONObject();
        json2.put("a", "1");
        json2.put("b", "12.232323141414");
        json2.put("c", "你好");
        json2.put("pk", request.getInt("pk"));


        JSONObject response = restServiceAPI.rest(url2, json2);

        logger.debug(response.toString());

        return ResponseData.success(response);
    }


}
