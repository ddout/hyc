package com.ddout.hyc.services;

import com.ddout.hyc.bean.PageBean;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.vo.User;

public interface IUserService {
  void add(RequestData requestData);
  void del(RequestData requestData);
  void update(RequestData requestData);
  PageBean<User> listPage(RequestData requestData);
}
