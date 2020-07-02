package com.ll.admin.dao;

import com.ll.admin.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,String> {

    Menu findByName (@Param( "name" ) String name);
}
