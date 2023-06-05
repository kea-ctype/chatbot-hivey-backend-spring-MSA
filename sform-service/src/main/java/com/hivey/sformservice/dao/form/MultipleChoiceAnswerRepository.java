package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.answer.MultipleChoiceAnswer;
import com.hivey.sformservice.domain.question.MultipleChoiceOption;
import com.hivey.sformservice.domain.space.SpaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel="multiple-choice-answer", path="multiple-choice-answer")
public interface MultipleChoiceAnswerRepository extends JpaRepository<MultipleChoiceAnswer, Long> {
    List<MultipleChoiceAnswer> findAllByOptionAndMember(MultipleChoiceOption option, SpaceMember spaceMember);

    List<MultipleChoiceAnswer> findAllByOption(MultipleChoiceOption multipleChoiceOption);

    Long countByOption(MultipleChoiceOption multipleChoiceOption);
}
