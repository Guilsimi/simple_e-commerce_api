package com.example.ec_user.entities.dto;

import java.util.HashSet;
import java.util.Set;

import com.example.ec_user.entities.Role;
import com.example.ec_user.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "email cannot be blank")
    @Email(message = "Please, insert an valid email format")
    private String email;

    @NotBlank(message = "phone is required")
    private String phone;

    @NotBlank(message = "name cannot be blank")
    @Size(min = 5, max = 50, message = "name must be 2-50 characters")
    private String name;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, max = 20, message = "password must be 8-20 characters")
    private String password;

    @NotBlank(message = "address is required")
    private String address;

    private Set<Role> roles = new HashSet<>();

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.address = user.getAddress();
        this.phone = user.getPhone();
    }

}
