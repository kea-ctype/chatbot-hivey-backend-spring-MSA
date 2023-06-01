package com.hivey.sformservice.domain.space;

import com.hivey.sformservice.domain.form.FormTargetGroup;
import com.hivey.sformservice.global.common.BaseTime;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 스페이스 사용자 그룹
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@Where(clause = "status = 'Y'")
@Table(name = "space_group")
@Entity
public class SpaceGroup extends BaseTime implements Serializable {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    // FIXME: 이름은 스페이스 하나당 중복되지 않게 처리해야 한다. (그룹 생성 시 처리할 것)
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    private char status;

    @OneToMany(mappedBy = "group")
    private List<SpaceMember> members;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<FormTargetGroup> formTargetGroups = new ArrayList<>();

    @Builder
    public SpaceGroup(Space space, String name) {
        this.space = space;
        this.name = name;
        this.status = 'Y';
    }

    /**
     * 스페이스 그룹의 이름을 수정한다.
     */
    public void updateSpaceGroupName(String name) {
        this.name = name;
    }

    /**
     * 스페이스 그룹을 삭제한다.
     */
    public void deleteSpaceGroup() {
        this.status = 'N';
    }
}