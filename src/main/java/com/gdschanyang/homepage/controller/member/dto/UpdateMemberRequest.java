package com.gdschanyang.homepage.controller.member.dto;

import com.gdschanyang.homepage.advice.ValidationGroups;
import com.gdschanyang.homepage.domain.member.enumerate.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRequest {
    @NotBlank(message = "이름은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String name;
    private Part part;
    private String description;
}
