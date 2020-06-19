package com.ll.admin.service;

import java.util.List;
import java.util.Map;

public interface OtherService {

    //城市查询
    List<Map<String,Object>> findAllCitys(Integer pid);

    //民族查询
    List<Map<String, Object>> findAllNations(Integer id);
}
