package com.hivey.sformservice.domain.space;


import com.hivey.sformservice.domain.form.Form;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "status = 'Y'")
@Table(name = "space")
@Entity
public class Space extends BaseTime {
    @Id
    @Column(name = "space_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String img;

    @NotNull
    @Column(name = "access_code")
    private String accessCode;

    @NotNull
    @Column(name = "headcount")
    private int headcount;

    @NotNull
    @Column(name = "status")
    private char status;

    @OneToMany(mappedBy = "space", cascade = CascadeType.REMOVE)
    private List<SpaceMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "space", cascade = CascadeType.REMOVE)
    private List<SpaceGroup> groups = new ArrayList<>();

    @OneToMany(mappedBy = "space", cascade = CascadeType.REMOVE)
    private List<Form> forms = new ArrayList<>();

    @Builder
    public Space(String name, String img, String accessCode, int headcount, char status) {
        this.name = name;
        this.img = img;
        this.accessCode = accessCode;
        this.headcount = headcount;
        this.status = 'Y';
    }

    /**
     * 스페이스 멤버의 상태를 변경할 때 사용한다.
     */
    public void deleteSpace() {
        this.status = 'N';
    }

    /**
     * 스페이스의 정보를 수정할 때 사용한다.
     */
    public void updateSpace(String name, String img) {
        this.name = name;
        this.img = img;
    }

}
