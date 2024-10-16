package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
