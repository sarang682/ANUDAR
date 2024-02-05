package com.ssafy.anudar.repository;

import com.ssafy.anudar.model.User;
import com.ssafy.anudar.model.UserRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByRole(UserRole role);
    

    void deleteById(Long id);

}
