package com.ll.admin.service.impl;


import com.ll.admin.mapper.OtherMapper;
import com.ll.admin.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OtherServiceImpl implements OtherService {

    @Autowired
    private OtherMapper otherMapper;

    @Override
    public List<Map<String, Object>> findAllCitys(String pid) {
        return this.otherMapper.findAllCitys( pid );
    }

    @Override
    public List<Map<String, Object>> findAllNations(String id) {
        return this.otherMapper.findAllNations( id );
    }
}
