package com.exam.smartpark.user.repository;

import com.exam.smartpark.user.entity.SmartParkUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmartParkUserRepository extends JpaRepository<SmartParkUser, Long> {

    Optional<SmartParkUser> findByUsername(String username);
}
