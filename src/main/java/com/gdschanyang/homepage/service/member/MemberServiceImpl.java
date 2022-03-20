package com.gdschanyang.homepage.service.member;

import com.gdschanyang.homepage.configuration.security.JwtTokenProvider;
import com.gdschanyang.homepage.controller.member.dto.*;
import com.gdschanyang.homepage.domain.member.*;
import com.gdschanyang.homepage.domain.member.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository tokenRepository;
    private final EmailTokenServiceImpl emailTokenService;

    @Transactional
    public void join(JoinMemberRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) throw new CAlreadyJoinMemberException();
        Member member = Member.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName()).part(request.getPart()).role("ROLE_USER").enable(false).build();
        memberRepository.save(member);
        emailTokenService.createEmailToken(member.getId(), request.getEmail());
    }

    @Transactional
    public void confirmEmail(String token) {
        EmailToken findConfirmationToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);
        Member member = memberRepository.findById(findConfirmationToken.getMemberId()).orElseThrow(CNotFoundMemberException::new);
        findConfirmationToken.useToken();	// 이메일 토큰 만료 ex) expired = true
        member.emailCheck();	// 유저의 이메일 인증 값 변경 ex) enable = true
    }

    @Transactional
    public TokenDto login(LoginMemberRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(CNotFoundMemberException::new);
        if (!member.getEnable()) throw new CNotEnableMemberException();
        if (tokenRepository.findByUserId(member.getId()).isPresent()) {
            RefreshToken refreshToken = tokenRepository.findByUserId(member.getId()).orElseThrow(CNotFoundRefreshTokenException::new);
            tokenRepository.delete(refreshToken);
        }
        TokenDto tokenDto = jwtTokenProvider.createTokenDto(member.getId(), member.getRole());
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(member.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequest request) {
        if (!jwtTokenProvider.validationToken(request.getRefreshToken())) throw new CConflictRefreshTokenException();
        Authentication authentication = jwtTokenProvider.getAuthentication(request.getAccessToken());
        Member member = memberRepository.findByEmail(authentication.getName()).orElseThrow(CNotFoundMemberException::new);
        RefreshToken refreshToken = tokenRepository.findByUserId(member.getId()).orElseThrow(CNotFoundRefreshTokenException::new);
        if (!refreshToken.getToken().equals(request.getRefreshToken())) throw new CConflictRefreshTokenException();

        TokenDto newCreatedToken = jwtTokenProvider.createTokenDto(member.getId(), member.getRole());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenRepository.save(updateRefreshToken);
        return newCreatedToken;
    }

    @Transactional
    public void logout(Long memberId) {
        RefreshToken refreshToken = tokenRepository.findByUserId(memberId).orElseThrow(CNotFoundRefreshTokenException::new);
        tokenRepository.delete(refreshToken);
    }

    @Transactional(readOnly = true)
    public MemberDto memberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return new MemberDto(member);
    }

    @Transactional
    public void updateMember(Long userId, UpdateMemberRequest request) {
        Member modifiedMember = memberRepository.findById(userId).orElseThrow(CNotFoundMemberException::new);
        modifiedMember.update(request.getName(), request.getPart(), request.getDescription());
    }

    @Transactional
    public void updateMemberPassword(Long userId, UpdateMemberPasswordRequest request) {
        Member modifiedMember = memberRepository.findById(userId).orElseThrow(CNotFoundMemberException::new);
        modifiedMember.updatePassword(passwordEncoder.encode(request.getPassword()));
    }
}
