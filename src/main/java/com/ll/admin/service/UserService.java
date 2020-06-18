package com.ll.admin.service;

import com.ll.admin.domain.Login;

import java.util.List;

public interface UserService {

    List<Login> findAll();
}
