package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.question.MultipleChoiceOption;
import com.hivey.sformservice.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel="multipleChoiceOption", path="multipleChoiceOption")
public interface MultipleChoiceOptionRepository extends JpaRepository<MultipleChoiceOption, Long> {
    List<MultipleChoiceOption> findAllByQuestion(Question q);
}
