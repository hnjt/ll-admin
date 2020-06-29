package com.ll.admin.dao;

import com.ll.admin.domain.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary,String> {

    /**
     * 根据code查询结果集
     * @param code
     * @return
     */
    List<Dictionary> findAllByCode(@Param("code") String code);

    /**
     * 根据pCode查询结果集
     * @param parentCode
     * @return
     */
    List<Dictionary> findAllByParentCode(@Param("parentCode") String parentCode);
}
