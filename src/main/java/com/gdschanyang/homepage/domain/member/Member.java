package com.gdschanyang.homepage.domain.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdschanyang.homepage.domain.common.BaseEntity;
import com.gdschanyang.homepage.domain.member.enumerate.Part;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Part part;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private Boolean enable;

    private String description;
    private String image;

    @Builder
    private Member(String email, String password, String name, Part part, String role, Boolean enable) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.part = part;
        this.role = role;
        this.enable = enable;
    }

    public void emailCheck() {
        this.enable = true;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void update(String name, Part part, String description) {
        this.name = name;
        this.part = part;
        this.description = description;
    }
}
