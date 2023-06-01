package com.hivey.sformservice.domain.form;

import com.hivey.sformservice.domain.question.Question;
import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 설문
 * - Entity 완료
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "form")
@Entity
public class Form extends BaseTime {

    @Id
    @Column(name = "form_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private SpaceMember creator;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "is_anonymous")
    private char isAnonymous = 'N';

    @Column(name = "status")
    private char status = 'Y';

    @Column(name = "is_mandatory")
    private char isMandatory = 'N';

    @OneToMany(mappedBy = "form", cascade = CascadeType.REMOVE)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "form", cascade = CascadeType.REMOVE)
    private List<Submission> submissions = new ArrayList<>();

    @OneToMany(mappedBy = "form", cascade = CascadeType.REMOVE)
    private List<FormTargetGroup> formTargetGroups = new ArrayList<>();

    @Builder
    public Form(Space space, SpaceMember creator, String title, String content, Date startDate, Date endDate, char isAnonymous, char status, char isMandatory) {
        this.space = space;
        this.creator = creator;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAnonymous = isAnonymous;
        this.status = 'Y';
        this.isMandatory = isMandatory;
    }


//    public void saveForm(RegisterFormRequest form) {
//        this.title = form.getTitle();
//        this.content = form.getContent();
//        this.startDate = form.getStartDate();
//        this.endDate = form.getEndDate();
//        this.isAnonymous = form.getIsAnonymous();
//        this.isMandatory = form.getIsMandatory();
//    }

//    public void updateForm(RegisterFormRes form) {
//        this.title = form.getTitle();
//        this.content = form.getContent();
//        this.startDate = form.getStartDate();
//        this.endDate = form.getEndDate();
//        this.isAnonymous = form.getIsAnonymous();
//        this.isMandatory = form.getIsMandatory();
//    }

//    public void deleteStatus(char status) {
//        this.status = status;
//    }


}