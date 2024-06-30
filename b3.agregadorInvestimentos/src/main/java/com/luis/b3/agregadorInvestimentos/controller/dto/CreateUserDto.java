package com.luis.b3.agregadorInvestimentos.controller.dto;

import com.luis.b3.agregadorInvestimentos.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String password;


    public User toModel() {
        return new User(
                UUID.randomUUID(),
                this.username,
                this.email,
                this.password,
                Instant.now(),
                null
        );

    }


}
