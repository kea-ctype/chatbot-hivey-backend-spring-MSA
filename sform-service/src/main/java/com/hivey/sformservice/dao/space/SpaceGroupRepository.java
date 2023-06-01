package com.hivey.sformservice.dao.space;

import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "space-group", path = "space-group")
public interface SpaceGroupRepository extends JpaRepository<SpaceGroup, Long> {

    Optional<SpaceGroup> findOneByGroupIdAndSpace(Long groupId, Space space);

    Optional<SpaceGroup> findOneBySpaceAndName(Space space, String name);

    List<SpaceGroup> findAllBySpace(Space space);
}