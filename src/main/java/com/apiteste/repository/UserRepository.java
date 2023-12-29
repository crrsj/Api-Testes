package com.apiteste.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiteste.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}
