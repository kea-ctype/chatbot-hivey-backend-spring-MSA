package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.domain.form.Submission;
import com.hivey.sformservice.domain.space.SpaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel="submission", path="submission")
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Optional<Submission> findOneByFormAndMember(Form f, SpaceMember spaceMember);
}

