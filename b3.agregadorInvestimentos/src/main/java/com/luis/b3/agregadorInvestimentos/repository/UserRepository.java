package com.luis.b3.agregadorInvestimentos.repository;

import com.luis.b3.agregadorInvestimentos.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
