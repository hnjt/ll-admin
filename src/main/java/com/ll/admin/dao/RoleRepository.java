package com.ll.admin.dao;

import com.ll.admin.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    Role getRoleByName(@Param( "name" ) String name);
}
