package com.hivey.sformservice.dao.form;


import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.domain.form.FormTargetGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel="formTargetGroup", path="formTargetGroup")
public interface FormTargetGroupRepository extends JpaRepository<FormTargetGroup, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO form_target_group(form_id, group_id, created_date, modified_date) VALUES(:formId, :groupId, now(), now())", nativeQuery = true)
    int saveGroup(@Param("formId") Long formId, @Param("groupId") Long groupId);

    List<FormTargetGroup> findAllByForm(Form form);
}
