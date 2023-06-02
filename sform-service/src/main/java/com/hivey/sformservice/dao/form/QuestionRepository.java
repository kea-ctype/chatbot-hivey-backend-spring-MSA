package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel="question", path="question")
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findAllByQuestionId(Long questionId);
}
