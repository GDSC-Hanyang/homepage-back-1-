package com.gdschanyang.homepage.service.member;

import com.gdschanyang.homepage.controller.member.dto.*;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
public interface MemberService {
    void join(JoinMemberRequest request);
    void confirmEmail(String token);
    TokenDto login(LoginMemberRequest request);
    TokenDto reissue(TokenRequest request);
    void logout(Long memberId);
    MemberDto memberInfo(Long memberId);
    void updateMember(Long userId, UpdateMemberRequest request);
    void updateMemberPassword(Long userId, UpdateMemberPasswordRequest request);
}
