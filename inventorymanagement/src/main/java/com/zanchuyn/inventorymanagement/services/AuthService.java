package com.zanchuyn.inventorymanagement.services;

import com.zanchuyn.inventorymanagement.dtos.request.LoginRequest;
import com.zanchuyn.inventorymanagement.entities.User;
import com.zanchuyn.inventorymanagement.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUser(LoginRequest loginRequest){
        return userRepository.findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
