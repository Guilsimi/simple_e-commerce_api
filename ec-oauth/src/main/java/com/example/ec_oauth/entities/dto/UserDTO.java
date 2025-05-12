package com.example.ec_oauth.entities.dto;

import java.util.HashSet;
import java.util.Set;

import com.example.ec_oauth.entities.Role;
import com.example.ec_oauth.entities.UserEntity;

public class UserDTO {

    private Long id;
    private String email;
    private String phone;
    private String name;
    private String password;
    private String address;
    private Set<Role> roles = new HashSet<>();

    public UserDTO() {

    }

    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.address = user.getAddress();
        this.phone = user.getPhone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

}
