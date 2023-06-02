package com.hivey.sformservice.dao.form;

import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.domain.space.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel="form", path="form")
public interface FormRepository extends JpaRepository<Form, Long> {

    /**
     * 설문지 목록 조회
     */
    List<Form> findAllBySpace(Space space);

    /**
     * 설문지 삭제하기
     */
    Optional<Form> findAllByFormId(Long formId);

    /**
     * 해당 식별번호를 가진 Form 찾기
     */
    Optional<Form> findById(Long formId);

    Optional<Form> findByFormId(Long formId);
}