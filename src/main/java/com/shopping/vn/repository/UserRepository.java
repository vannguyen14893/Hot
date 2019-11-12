package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.vn.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByEmail(String email);
}
