package com.gdschanyang.homepage.controller.member.dto;

import com.gdschanyang.homepage.advice.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberPasswordRequest {
    @NotBlank(message = "비밀번호는 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Length(min = 8, message = "비밀번호는 최소 {min}자 이상입니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String password;
}
