package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="answer", path="answer")
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
