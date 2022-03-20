package com.gdschanyang.homepage.controller.member.dto;

import com.gdschanyang.homepage.advice.ValidationGroups;
import com.gdschanyang.homepage.domain.member.enumerate.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinMemberRequest {
    @NotBlank(message = "이메일은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Email(regexp = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}",
            message = "Email 형식이 아닙니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String email;
    @NotBlank(message = "비밀번호는 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Length(min = 8, message = "비밀번호는 최소 {min}자 이상입니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String password;
    @NotBlank(message = "이름은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String name;
    private Part part;
}
