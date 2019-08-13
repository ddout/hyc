package com.ddout.hyc.services;

import com.ddout.hyc.vo.User;

public interface IUserService {
  User findUserByName(String name);
}
