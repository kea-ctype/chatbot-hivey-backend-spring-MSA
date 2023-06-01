package com.hivey.sformservice.domain.space;

import com.hivey.sformservice.domain.answer.Answer;
import com.hivey.sformservice.domain.answer.MultipleChoiceAnswer;
import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.domain.form.Submission;
import com.hivey.sformservice.global.common.BaseTime;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 스페이스 멤버
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@Where(clause = "status = 'Y'")
@Table(name = "space_member")
@Entity
public class SpaceMember extends BaseTime {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private SpaceGroup group;

    @NotNull
    @Column(name = "position")
    private char position = 'P';

    @NotNull
    @Column(name = "status")
    private char status = 'Y';

    @OneToMany(mappedBy = "member")
    private List<MultipleChoiceAnswer> multipleChoiceAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Answer> shortAnswerResponses = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Submission> formMembers = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Form> forms;

    @Builder
    public SpaceMember(Space space, Long userId, SpaceGroup group, char position, char status) {
        this.space = space;
        this.userId = userId;
        this.group = group;
        this.position = position;
        this.status = status;
    }

    /**
     * 스페이스 멤버의 그룹을 변경할 때 사용한다.
     */
    public void updateGroup(SpaceGroup group) {
        this.group = group;
    }
}