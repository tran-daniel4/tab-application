package com.tab.tab_application.user.repository;

import com.tab.tab_application.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByPhoneNumber(String phoneNumber);
    Optional<UserModel> findById(Long id);
}
