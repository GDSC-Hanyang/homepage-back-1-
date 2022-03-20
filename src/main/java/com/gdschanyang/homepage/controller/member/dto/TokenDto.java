package com.gdschanyang.homepage.controller.member.dto;

import lombok.*;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpireDate;
}