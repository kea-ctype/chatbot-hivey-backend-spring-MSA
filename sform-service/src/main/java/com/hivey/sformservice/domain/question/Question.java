package com.hivey.sformservice.domain.question;

import com.hivey.sformservice.domain.answer.Answer;
import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 설문 문항
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "form_question")
@Entity
public class Question extends BaseTime {

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    /**
     * M: Multiple choice
     * S: Short answer
     */
    @Column(name = "type")
    private char type;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private char status;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @OneToMany(mappedBy = "question")
    private List<MultipleChoiceOption> options = new ArrayList<>();

    @Builder
    public Question(Form form, char type, String title, String content, char status) {
        this.form = form;
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = 'Y';
    }

    public void deleteStatus(char status) {
        this.status = status;
    }
}