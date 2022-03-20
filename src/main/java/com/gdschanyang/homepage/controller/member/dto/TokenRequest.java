package com.gdschanyang.homepage.controller.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Getter
@NoArgsConstructor
public class TokenRequest {
    @NotBlank(message = "access token 은 필수값입니다.")
    String accessToken;
    @NotBlank(message = "refresh token 은 필수값입니다.")
    String refreshToken;

    @Builder
    public TokenRequest(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}