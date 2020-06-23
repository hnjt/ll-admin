package com.ll.admin.service;

import java.util.List;
import java.util.Map;

public interface OtherService {

    /**
     * 根据PID城市查询
     * pid ：null 查询所有省级单位
     * @param pid
     * @return
     */
    List<Map<String,Object>> findAllCitys(String pid);

    /**
     * 根据ID民族查询
     * id：null查询所有民族
     * @param id
     * @return
     */
    List<Map<String, Object>> findAllNations(String id);
}
