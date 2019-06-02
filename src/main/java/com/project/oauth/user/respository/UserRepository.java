package com.project.oauth.user.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.oauth.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
