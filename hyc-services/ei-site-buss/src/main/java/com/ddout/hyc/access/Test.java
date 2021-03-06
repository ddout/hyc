package com.ddout.hyc.access;

import com.alibaba.fastjson.JSONObject;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.bean.ResponseData;
import com.ddout.hyc.exceptions.BizException;
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

    @RequestMapping(value = "test")
    public ResponseData test(@RequestBody RequestData request){
        logger.info(JSONObject.toJSONString(sessionPropagationBean.getAll()));
        logger.info(request.toString());
        if(1 == request.getInt("pk")){
            throw new BizException("这里是PK=111111111");
        }
        if(2 == request.getInt("pk")){
            throw new RuntimeException("这里是PK=222222");
        }
        return ResponseData.success(request.getParamJson());
    }

}
