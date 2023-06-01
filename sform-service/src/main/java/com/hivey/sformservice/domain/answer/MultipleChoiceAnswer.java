package com.hivey.sformservice.domain.answer;

import com.hivey.sformservice.domain.question.MultipleChoiceOption;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.global.common.BaseTime;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 설문 응답 (객관식)
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "multiple_choice_answer")
@Entity
public class MultipleChoiceAnswer extends BaseTime {

    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private SpaceMember member;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private MultipleChoiceOption option;

    @NotNull
    @Column(name = "status")
    private char status;

    @Builder
    public MultipleChoiceAnswer(SpaceMember member, MultipleChoiceOption option, char status) {
        this.option = option;
        this.member = member;
        this.status = 'Y';
    }

    public void deleteStatus(char status) {
        this.status = status;
    }
}