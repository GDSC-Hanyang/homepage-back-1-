package com.gdschanyang.homepage.controller.member.dto;

import com.gdschanyang.homepage.domain.member.Member;
import com.gdschanyang.homepage.domain.member.enumerate.Part;
import lombok.Getter;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Getter
public class MemberDto {
    private final Long id;
    private final String email;
    private final String name;
    private final Part part;
    private final String role;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.part = member.getPart();
        this.role = member.getRole();
    }
}
