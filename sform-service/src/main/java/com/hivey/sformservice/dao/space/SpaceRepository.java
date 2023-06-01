package com.hivey.sformservice.dao.space;
import com.hivey.sformservice.domain.space.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(collectionResourceRel="space", path="space")
public interface SpaceRepository extends JpaRepository<Space, Long> {

    Optional<Space> findOneByAccessCode(String accessCode);
}