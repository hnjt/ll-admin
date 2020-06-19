package com.ll.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OtherMapper {

    //城市查询
    List<Map<String,Object>> findAllCitys(@Param("pid") Integer pid);

    //民族查询
    List<Map<String, Object>> findAllNations(@Param("id") Integer id);
}
