package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.answer.MultipleChoiceAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="multiple-choice-answer", path="multiple-choice-answer")
public interface MultipleChoiceAnswerRepository extends JpaRepository<MultipleChoiceAnswer, Long> {
}
