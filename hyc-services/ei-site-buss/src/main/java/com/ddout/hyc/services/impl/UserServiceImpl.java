package com.ddout.hyc.services.impl;

import com.ddout.hyc.dao.IUserDao;
import com.ddout.hyc.services.IUserService;
import com.ddout.hyc.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private IUserDao userDao;

  @Override
  public User findUserByName(String name) {
    return userDao.findUserByName(name);
  }


}
