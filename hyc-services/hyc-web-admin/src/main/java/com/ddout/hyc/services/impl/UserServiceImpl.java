package com.ddout.hyc.services.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ddout.hyc.bean.PageBean;
import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.dao.IUserDao;
import com.ddout.hyc.service.BaseService;
import com.ddout.hyc.services.IUserService;
import com.ddout.hyc.vo.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseService<User> implements IUserService {

  @Autowired
  private IUserDao userDao;

  @Override
  public void add(RequestData requestData) {
    User user = new User();
    user.setUsername(requestData.getStringValNotBlank("username"));
    user.setPassword(requestData.getStringValNotBlank("password"));
    userDao.insert(user);
  }

  @Override
  public void del(RequestData requestData) {
    userDao.deleteById(requestData.getStringValNotBlank("id"));
  }

  @Override
  public void update(RequestData requestData) {
    User user = new User();
    user.setId(requestData.getStringValNotBlank("id"));
    user.setUsername(requestData.getStringValNotBlank("username"));
    user.setPassword(requestData.getStringValNotBlank("password"));
    userDao.updateById(user);
  }

  @Override
  public List<User> list(Map<String, Object> map) {
    return userDao.list(map);
  }

  @Override
  public int listCount(Map<String, Object> map) {
    return userDao.listCount(map);
  }


}
