package com.hivey.sformservice.domain.question;

import com.hivey.sformservice.domain.answer.MultipleChoiceAnswer;
import com.hivey.sformservice.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 설문 문항 (객관식 옵션)
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "multiple_choice_option")
@Entity
public class MultipleChoiceOption extends BaseTime {

    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "option_content")
    private String optionContent;

    @Column(name = "status")
    private char status;

    @OneToMany(mappedBy = "option")
    private List<MultipleChoiceAnswer> answers;

    @Builder
    public MultipleChoiceOption(Question question, String optionContent, char status) {
        this.question = question;
        this.optionContent = optionContent;
        this.status = 'Y';
    }

    public void deleteStatus(char status) {
        this.status = status;
    }

}