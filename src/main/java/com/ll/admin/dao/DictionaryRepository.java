package com.ll.admin.dao;

import com.ll.admin.domain.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary,String> {

}
