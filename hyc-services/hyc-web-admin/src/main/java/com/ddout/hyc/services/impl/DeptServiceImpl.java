package com.ddout.hyc.services.impl;

import com.ddout.hyc.bean.RequestData;
import com.ddout.hyc.dao.IDeptDao;
import com.ddout.hyc.service.BaseService;
import com.ddout.hyc.services.IDeptService;
import com.ddout.hyc.services.IDeptService;
import com.ddout.hyc.vo.Dept;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl extends BaseService<Map> implements IDeptService {

  @Autowired
  private IDeptDao deptDao;

  @Override
  public void add(RequestData requestData) {
    Dept dept = new Dept();
    dept.setName(requestData.getStringValNotBlank("name"));
    dept.setCode(requestData.getStringValNotBlank("code"));
    dept.setPid(requestData.getStringValNotBlank("pid"));
    dept.setSortNum(requestData.getIntVal("sortNum"));
    deptDao.insert(dept);
  }

  @Override
  public void del(RequestData requestData) {
    deptDao.deleteById(requestData.getStringValNotBlank("id"));
  }

  @Override
  public void update(RequestData requestData) {
    Dept dept = new Dept();
    dept.setId(requestData.getStringValNotBlank("id"));
    dept.setName(requestData.getStringValNotBlank("name"));
    dept.setCode(requestData.getStringValNotBlank("code"));
    dept.setPid(requestData.getStringValNotBlank("pid"));
    dept.setSortNum(requestData.getIntVal("sortNum"));
    deptDao.updateById(dept);
  }

  @Override
  public List<Map> list(Map<String, Object> map) {
    return deptDao.list(map);
  }

  @Override
  public int listCount(Map<String, Object> map) {
    return deptDao.listCount(map);
  }


}
