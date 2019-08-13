package com.ddout.hyc.control;

import com.ddout.hyc.bean.PageBean;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.bean.ResponseData;
import com.ddout.hyc.services.IDeptService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= "dept")
public class DeptControl {

  @Autowired
  private IDeptService deptService;

  @RequestMapping(value = "add")
  public ResponseData add(@RequestBody RequestData requestData) {
    deptService.add(requestData);
    return ResponseData.success();
  }

  @RequestMapping(value = "del")
  public ResponseData del(@RequestBody RequestData requestData) {
    deptService.del(requestData);
    return ResponseData.success();
  }

  @RequestMapping(value = "update")
  public ResponseData update(@RequestBody RequestData requestData) {
    deptService.update(requestData);
    return ResponseData.success();
  }

  @RequestMapping(value = "listPage")
  public ResponseData listPage(@RequestBody RequestData requestData) {
    PageBean<Map> page = deptService.listPage(requestData);
    return ResponseData.success(page);
  }


}
