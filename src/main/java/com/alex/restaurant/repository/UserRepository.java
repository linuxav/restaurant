package com.alex.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alex.restaurant.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
