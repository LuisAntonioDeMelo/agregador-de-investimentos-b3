package com.luis.b3.agregadorInvestimentos.service;

import com.luis.b3.agregadorInvestimentos.controller.dto.CreateUserDto;
import com.luis.b3.agregadorInvestimentos.controller.dto.UpdateUserDto;
import com.luis.b3.agregadorInvestimentos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.UUID;
import com.luis.b3.agregadorInvestimentos.domain.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public UUID createUser(CreateUserDto createUserDto) {
        var user = createUserDto.toModel();
        var userSaved = userRepository.save(user);
        return userSaved.getId();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var userEntity = userRepository.findById(UUID.fromString(userId));
        if (userEntity.isPresent()) {
            var user = userEntity.get();
            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }
            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);
        if (userExists) {
            userRepository.deleteById(id);
        }
    }
}
