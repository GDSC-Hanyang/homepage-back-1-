package com.gdschanyang.homepage.controller.member;

import com.gdschanyang.homepage.advice.ValidationSequence;
import com.gdschanyang.homepage.controller.common.ApiResponse;
import com.gdschanyang.homepage.controller.member.dto.*;
import com.gdschanyang.homepage.domain.member.CurrentMember;
import com.gdschanyang.homepage.domain.member.Member;
import com.gdschanyang.homepage.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Slf4j
@RestController
@RequestMapping("/gdsc")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void join(@RequestBody @Validated(ValidationSequence.class) JoinMemberRequest request) {
        memberService.join(request);
    }

    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void confirmByEmail(@Valid @RequestParam String token) {
        memberService.confirmEmail(token);
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TokenDto> login(@RequestBody @Validated(ValidationSequence.class) LoginMemberRequest request) {
        TokenDto tokenDto = memberService.login(request);
        return new ApiResponse<>(tokenDto);
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TokenDto> reissue(@RequestBody @Valid TokenRequest request) {
        return new ApiResponse<>(memberService.reissue(request));
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@CurrentMember Member member) {
        memberService.logout(member.getId());
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberDto> memberInfo(@CurrentMember Member member) {
        return new ApiResponse<>(memberService.memberInfo(member.getId()));
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public void updateMember
            (@CurrentMember Member member, @RequestBody @Validated(ValidationSequence.class) UpdateMemberRequest request) {
        memberService.updateMember(member.getId(), request);
    }

    @PatchMapping("/me/password")
    @ResponseStatus(HttpStatus.OK)
    public void updateMemberPassword
            (@CurrentMember Member member, @RequestBody @Validated(ValidationSequence.class) UpdateMemberPasswordRequest request) {
        memberService.updateMemberPassword(member.getId(), request);
    }
}
