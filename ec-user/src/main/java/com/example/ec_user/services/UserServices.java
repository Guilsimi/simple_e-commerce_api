package com.example.ec_user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_user.entities.User;
import com.example.ec_user.repositories.UserRepository;
import com.example.ec_user.services.exceptions.ResourceNotFoundException;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> userFound = userRepository.findById(id);
        return userFound.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public User findByEmail(String email) {
        Optional<User> userFound = userRepository.findByEmail(email);
        return userFound.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
