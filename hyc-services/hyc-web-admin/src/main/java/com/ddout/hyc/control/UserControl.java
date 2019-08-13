package com.ddout.hyc.control;

import com.ddout.hyc.bean.PageBean;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.bean.ResponseData;
import com.ddout.hyc.services.IUserService;
import com.ddout.hyc.vo.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= "user")
public class UserControl {

  @Autowired
  private IUserService userService;

  @RequestMapping(value = "add")
  public ResponseData add(@RequestBody RequestData requestData) {
    userService.add(requestData);
    return ResponseData.success();
  }

  @RequestMapping(value = "del")
  public ResponseData del(@RequestBody RequestData requestData) {
    userService.del(requestData);
    return ResponseData.success();
  }

  @RequestMapping(value = "update")
  public ResponseData update(@RequestBody RequestData requestData) {
    userService.update(requestData);
    return ResponseData.success();
  }

  @RequestMapping(value = "listPage")
  public ResponseData listPage(@RequestBody RequestData requestData) {
    PageBean<User> page = userService.listPage(requestData);
    return ResponseData.success(page);
  }


}
