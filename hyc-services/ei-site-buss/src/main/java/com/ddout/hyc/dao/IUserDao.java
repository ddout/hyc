package com.ddout.hyc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddout.hyc.vo.User;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface IUserDao extends BaseMapper<User> {
  /**
   * 通过名字查询用户信息
   */
  User findUserByName(String name);

}
