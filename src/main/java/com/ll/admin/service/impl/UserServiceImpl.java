package com.ll.admin.service.impl;

import com.ll.admin.dao.UserRepository;
import com.ll.admin.domain.Login;
import com.ll.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Login> findAll() {
        return this.userRepository.findAll();
    }
}
