package com.example.ec_user.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ec_user.entities.Role;
import com.example.ec_user.entities.User;
import com.example.ec_user.repositories.UserRepository;
import com.example.ec_user.services.exceptions.ObjectNotCreatedException;
import com.example.ec_user.services.exceptions.ObjectNotUpdatedException;
import com.example.ec_user.services.exceptions.ResourceNotFoundException;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User insert(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getRoles().add(new Role(2L, "ROLE_CLIENTE"));
            User createdUser = userRepository.save(user);
            return createdUser;
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Erro ao criar o usuário");
        }
    }

    public User findById(Long id) {
        Optional<User> userFound = userRepository.findById(id);
        return userFound.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public User findByEmail(String email) {
        Optional<User> userFound = userRepository.findByEmail(email);
        return userFound.orElse(null);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void update(User user) {
        try {
            User newUser = user;
            update(newUser, user);
            userRepository.save(newUser);
        } catch (Exception e) {
            throw new ObjectNotUpdatedException("Erro ao atualizar informações do usuário");
        }
    }

    private void update(User newUser, User user) {
        newUser.setId(
                user.getId() != null ? user.getId() : newUser.getId());
        newUser.setName(
                user.getName() != null ? user.getName() : newUser.getName());
        newUser.setEmail(
                user.getEmail() != null ? user.getEmail() : newUser.getEmail());
        newUser.setPhone(
                user.getPhone() != null ? user.getPhone() : newUser.getPhone());
        newUser.setAddress(
                user.getAddress() != null ? user.getAddress() : newUser.getAddress());
        newUser.setPassword(
                user.getPassword() != null ? user.getPassword() : newUser.getPassword());
        newUser.setRoles(
                user.getRoles() != null ? user.getRoles() : newUser.getRoles());
    }
}
