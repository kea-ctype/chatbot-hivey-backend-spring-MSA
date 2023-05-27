package com.hivey.userservice.dao;

import com.hivey.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel="user",path="user")
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);



    Long countByEmail(String email);

}