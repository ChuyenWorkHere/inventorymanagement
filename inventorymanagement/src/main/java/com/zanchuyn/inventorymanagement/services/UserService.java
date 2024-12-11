package com.zanchuyn.inventorymanagement.services;


import com.zanchuyn.inventorymanagement.dtos.UserDto;
import com.zanchuyn.inventorymanagement.dtos.request.PasswordRequest;
import com.zanchuyn.inventorymanagement.dtos.request.UserRequest;
import com.zanchuyn.inventorymanagement.entities.User;
import com.zanchuyn.inventorymanagement.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public UserDto addUser(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return mapper.map(userRepository.save(user), UserDto.class);
    }

    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDto findUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return mapper.map(user, UserDto.class);
        } else {
            return null;
        }

    }

    public UserDto updateUser(Integer id, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User upadtedUser = mapper.map(userRequest, User.class);
            upadtedUser.setUpdatedAt(LocalDateTime.now());
            upadtedUser.setPassword(user.get().getPassword());
            upadtedUser.setUsername(user.get().getUsername());
            upadtedUser.setCreatedAt(user.get().getCreatedAt());
            upadtedUser.setUserId(user.get().getUserId());
            userRepository.save(upadtedUser);
            return mapper.map(upadtedUser, UserDto.class);
        } else {
            return null;
        }
    }

    public boolean updatePassword(PasswordRequest passwordRequest, User user) {
        if (user.getPassword().equals(passwordRequest.getOldPassword())) {
            user.setPassword(passwordRequest.getNewPassword());
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
