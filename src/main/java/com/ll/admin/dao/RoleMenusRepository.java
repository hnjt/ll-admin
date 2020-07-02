package com.ll.admin.dao;

import com.ll.admin.domain.RoleMenus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenusRepository extends JpaRepository<RoleMenus, String> {

    void deleteAllByRoleIdAndMenusId(@Param( "roleId" ) String roleId,@Param( "menusId" ) String menusId);

    void deleteAllByRoleId(@Param( "roleId" ) String roleId);
}
