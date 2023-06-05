package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.answer.Answer;
import com.hivey.sformservice.domain.question.Question;
import com.hivey.sformservice.domain.space.SpaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel="answer", path="answer")
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findOneByQuestionAndMember(Question question, SpaceMember spaceMember);

    List<Answer> findAllByQuestion(Question question);
}
