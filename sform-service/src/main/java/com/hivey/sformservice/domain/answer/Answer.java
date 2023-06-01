package com.hivey.sformservice.domain.answer;

import com.hivey.sformservice.domain.question.Question;
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
 * 설문 응답 (서술형)
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "answer")
@Entity
public class Answer extends BaseTime {

    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private SpaceMember member;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @NotNull
    @Column(name = "answer")
    private String answer;

    @NotNull
    @Column(name = "status")
    private char status;

    @Builder
    public Answer(SpaceMember member, Question question, String answer, char status) {
        this.member = member;
        this.question = question;
        this.answer = answer;
        this.status = 'Y';
    }

    public void deleteStatus(char status) {
        this.status = status;
    }
}