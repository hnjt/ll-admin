package com.ll.admin.dao;

import com.ll.admin.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login,String> {

    Login findByUsername (String username);
}
