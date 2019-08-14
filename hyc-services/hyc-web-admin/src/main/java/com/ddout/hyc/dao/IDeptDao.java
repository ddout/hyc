package com.ddout.hyc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddout.hyc.vo.Dept;
import java.util.List;
import java.util.Map;

public interface IDeptDao extends BaseMapper<Dept> {
  List<Dept> list(Map<String,Object> map);
  int listCount(Map<String,Object> map);
}
