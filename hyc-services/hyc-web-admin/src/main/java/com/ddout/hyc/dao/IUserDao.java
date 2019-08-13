package com.ddout.hyc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddout.hyc.vo.User;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDao extends BaseMapper<User> {

  List<User> list(Map<String,Object> map);
  int listCount(Map<String,Object> map);

}
