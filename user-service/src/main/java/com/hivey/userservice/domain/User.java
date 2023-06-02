package com.hivey.userservice.domain;

import com.hivey.userservice.global.common.BaseTime;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 사용자
 * - Entity 완료
 */
@Getter
@Table(name = "user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime implements Serializable {

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
