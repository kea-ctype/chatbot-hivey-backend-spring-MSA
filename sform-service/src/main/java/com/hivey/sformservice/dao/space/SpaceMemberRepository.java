package com.hivey.sformservice.dao.space;

import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceGroup;
import com.hivey.sformservice.domain.space.SpaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "space_member", path = "space_member")
public interface SpaceMemberRepository extends JpaRepository<SpaceMember, Long> {

    Optional<SpaceMember> findOneByUserIdAndSpace(Long userId, Space space);

    List<SpaceMember> findAllByUserId(Long userId);

    List<SpaceMember> findAllBySpace(Space space);

    List<SpaceMember> findAllByGroup(SpaceGroup spaceGroup);

    List<SpaceMember> findAllBySpaceAndGroup(Space space, SpaceGroup group);
}