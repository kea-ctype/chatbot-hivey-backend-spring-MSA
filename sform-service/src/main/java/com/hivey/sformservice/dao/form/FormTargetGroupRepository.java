package com.hivey.sformservice.dao.form;


import com.hivey.sformservice.domain.form.FormTargetGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="formTargetGroup", path="formTargetGroup")
public interface FormTargetGroupRepository extends JpaRepository<FormTargetGroup, Long> {

}
