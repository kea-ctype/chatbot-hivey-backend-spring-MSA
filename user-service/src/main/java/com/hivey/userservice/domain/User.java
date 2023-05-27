package com.hivey.userservice.domain;

import com.sun.istack.NotNull;
import ctype.survey.hivey.domain.space.domain.SpaceMember;
import ctype.survey.hivey.global.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자
 * - Entity 완료
 */
@Getter
@Table(name = "user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "img")
    private String img;

    @NotNull
    @Column(name = "status")
    private char status = 'Y';

    @OneToOne(mappedBy = "user")
    private AuthSocial authSocial;

    @OneToOne(mappedBy = "user")
    private AuthPassword authPassword;

    @OneToMany(mappedBy = "user")
    private List<SpaceMember> members = new ArrayList<>();

    @Builder
    public User(String name, String email, String img, char status) {
        this.name = name;
        this.email = email;
        this.img = img;
        this.status = 'Y';
    }

    /**
     * user의 정보를 수정할 때 사용
     */
    public void userUpdate(String name, String img){
        this.name=name;
        this.img=img;
    }


}
