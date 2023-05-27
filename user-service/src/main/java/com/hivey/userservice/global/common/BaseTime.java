package com.hivey.userservice.global.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 데이터가 생성된 시간과 수정된 시간을 자동으로 넣어주기 위한 클래스
 * JPA 엔티티들이 해당 클래스를 상속받는 경우 createdDate, modifiedDate를 인식하도록 한다.
 * 여기서 @MappedSuperclass는 직접 생성하기 보다는 해당 클래스를 상속받는 자식 클래스에게 매핑 정보만을 제공하고 싶을 때 사용한다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

    // 엔티티가 생성되어 저장될 때 시간이 자동으로 저장되도록 한다.
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    // 조회한 엔티티의 값을 변경할 때 시간이 자동으로 저장되도록 한다.
    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
