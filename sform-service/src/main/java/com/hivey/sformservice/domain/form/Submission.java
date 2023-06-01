package com.hivey.sformservice.domain.form;

import com.hivey.sformservice.domain.answer.MultipleChoiceAnswer;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.global.common.BaseTime;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 설문 참여 현황 확인 (스페이스 멤버)
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "submission")
@Entity
public class Submission extends BaseTime {

    @Id
    @Column(name = "submit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submitId;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private SpaceMember member;

    @NotNull
    @Column(name = "is_submit")
    private char isSubmit = 'N';

    @OneToMany(mappedBy = "member")
    private List<MultipleChoiceAnswer> multipleChoiceAnswers = new ArrayList<>();

    @Builder
    public Submission(SpaceMember member, Form form, char isSubmit) {
        this.member = member;
        this.form = form;
        this.isSubmit = 'N';
    }

    public void updateIsSubmit(char isSubmit) {
        this.isSubmit = isSubmit;
    }
}