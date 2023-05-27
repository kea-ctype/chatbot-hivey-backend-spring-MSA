package com.hivey.userservice.dao;

import com.hivey.userservice.domain.AuthPassword;
import com.hivey.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel="authPassword",path="authPassword")
public interface AuthPasswordRepository extends JpaRepository<AuthPassword, Long> {

    Optional<AuthPassword> findByUser(User user);
}
