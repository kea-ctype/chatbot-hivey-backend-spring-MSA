package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.form.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="submission", path="submission")
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

}

