package com.ddout.hyc.service;

import com.ddout.hyc.bean.PageBean;
import com.ddout.hyc.bean.RequestData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService<T> {


  public PageBean<T> listPage(RequestData requestData) {
    PageBean<T> pageBean = new PageBean<T>();
    pageBean.setCurrent(requestData.getIntVal("page",1));
    pageBean.setLimit(requestData.getIntVal("limit", 10));
    pageBean.getMySqlStart();
    Map<String,Object> map = new HashMap<String,Object>();
    map.putAll(requestData.getParamJson());
    map.put("limit", pageBean.getLimit());
    map.put("start", pageBean.getMySqlStart());
    //
    resetListPageParam(map);
    //
    int count = listCount(map);
    pageBean.setTotal(count);
    if(count >= 0){
      pageBean.setData(list(map));
    }
    return pageBean;
  }

  public abstract List<T> list(Map<String,Object> map);
  public abstract int listCount(Map<String,Object> map);

  /**
   * 处理过滤条件
   * @param map
   */
  public void resetListPageParam(Map<String,Object> map){

  };

}
