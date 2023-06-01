package com.hivey.sformservice.domain.form;

import com.hivey.sformservice.domain.space.SpaceGroup;
import com.hivey.sformservice.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 설문 참여 대상 (스페이스 그룹)
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "form_target_group")
@Entity
public class FormTargetGroup extends BaseTime {

    @Id
    @Column(name = "target_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long targetId;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private SpaceGroup group;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @Builder
    public FormTargetGroup(SpaceGroup group, Form form) {
        this.group = group;
        this.form = form;
    }
}