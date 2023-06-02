package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.question.MultipleChoiceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="multipleChoiceOption", path="multipleChoiceOption")
public interface MultipleChoiceOptionRepository extends JpaRepository<MultipleChoiceOption, Long> {
}
