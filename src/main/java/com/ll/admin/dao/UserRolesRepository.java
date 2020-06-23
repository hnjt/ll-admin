package com.ll.admin.dao;

import com.ll.admin.domain.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,String> {

    List<UserRoles> findByLoginId(@Param( "loginId" ) String loginId);

    void deleteByLoginId(@Param( "loginId" ) String loginId);
}
