package com.ddout.hyc.services;

import com.ddout.hyc.bean.PageBean;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.vo.Dept;
import com.ddout.hyc.vo.User;
import java.util.Map;

public interface IDeptService {
  void add(RequestData requestData);
  void del(RequestData requestData);
  void update(RequestData requestData);
  PageBean<Dept> listPage(RequestData requestData);
}
