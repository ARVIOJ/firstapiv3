package com.trainibit.first_api.service.impl;

import com.trainibit.first_api.entity.User;
import com.trainibit.first_api.mapper.UserMapper;
import com.trainibit.first_api.repository.UserRepository;
import com.trainibit.first_api.request.UserRequest;
import com.trainibit.first_api.response.UserResponse;
import com.trainibit.first_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserResponse> findAll() {
        return userMapper.entityToResponseList(userRepository.findAll());
    }


    @Override
    public UserResponse findByUuid(UUID uuid) {
        User userOptional = userRepository.findByUuid(uuid);
        // Manejar el caso en que el usuario no se encuentre
        return userOptional != null ? userMapper.entityToResponse(userOptional) : null;
    }
    @Override
    public User requestToEntity(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setBirthday(userRequest.getBirthdate());
        user.setEmail(userRequest.getEmail());
        user.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setUuid(UUID.randomUUID());
        return user;
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        User user = requestToEntity(userRequest);
        User savedUser = userRepository.save(user);
        return entityToResponse(savedUser);
    }

    private UserResponse entityToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setUuid(UUID.randomUUID());
        return userResponse;
    }

}
