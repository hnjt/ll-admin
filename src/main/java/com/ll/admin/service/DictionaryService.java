package com.ll.admin.service;

import com.ll.admin.domain.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryService {

    /**
     * 根据PCode 查询数据字典
     * @param params
     * @return
     */
    List<Dictionary> findAllDictionaryByPCode (Map<String,String> params);

    /**
     * 根据ID 删除数据字典
     * @param params
     * @return
     */
    Boolean deleteDictionary(Map<String,String> params);

    /**
     * 添加/编辑数据字典
     * @param params
     * @return
     */
    Boolean saveOrUpdateDictionary(Map<String,String> params);
}
