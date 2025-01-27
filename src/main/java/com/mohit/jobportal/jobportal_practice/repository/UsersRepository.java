package com.mohit.jobportal.jobportal_practice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohit.jobportal.jobportal_practice.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	Users getUserByEmail(String email);

	Optional<Users> findByEmail(String username);

}
