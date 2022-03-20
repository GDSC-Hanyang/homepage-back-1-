package com.gdschanyang.homepage.controller.member;

import com.gdschanyang.homepage.controller.member.dto.*;
import com.gdschanyang.homepage.domain.member.EmailToken;
import com.gdschanyang.homepage.domain.member.Member;
import com.gdschanyang.homepage.domain.member.enumerate.Part;
import com.gdschanyang.homepage.service.member.EmailTokenService;
import com.gdschanyang.homepage.service.member.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTest {
    private Member member;
    private TokenDto token;

    @Autowired
    MemberService memberService;

    @Autowired
    EmailTokenService emailTokenService;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAllInBatch();
        emailTokenRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        member = memberRepository.save(Member.builder()
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .part(Part.DEVELOP)
                .enable(true)
                .role("ROLE_USER")
                .build());
        token = memberService.login(LoginMemberRequest.builder().email("email@email.com").password("password").build());
    }

    @AfterEach
    void tearDown() {
        refreshTokenRepository.deleteAllInBatch();
        emailTokenRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    void join() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(JoinMemberRequest.builder()
                .email("new@email.com").password("new_password").name("James").part(Part.DESIGN).build());

        //then
        ResultActions actions = mockMvc.perform(post("/gdsc/join")
                .content(object).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        //when
        actions
                .andDo(print())
                .andDo(document("members/join",
                        preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("멤버 이메일"),
                                fieldWithPath("password").description("멤버 비밀번호"),
                                fieldWithPath("name").description("멤버 이름"),
                                fieldWithPath("part").description("멤버 파트")
                        )
                ))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void confirmByEmail() throws Exception {
        //given
        String emailToken = emailTokenRepository.save(EmailToken.createEmailToken(member.getId())).getId();
        //then
        ResultActions actions = mockMvc.perform(get("/gdsc/email?token=" + emailToken));
        //when
        actions
                .andDo(print())
                .andDo(document("members/email",
                        preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("token").description("이메일 인증 토큰")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void login() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(LoginMemberRequest.builder()
                .email("email@email.com")
                .password("password")
                .build());
        //when
        ResultActions actions = mockMvc.perform(post("/gdsc/login")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("members/login",
                        preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("멤버 이메일"),
                                fieldWithPath("password").description("멤버 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("data.grantType").description("grantType"),
                                fieldWithPath("data.accessToken").description("accessToken"),
                                fieldWithPath("data.refreshToken").description("refreshToken"),
                                fieldWithPath("data.accessTokenExpireDate").description("accessToken 만료시간")
                        )
                ));
    }

    @Test
    void reissue() throws Exception {
        //given
        TokenDto userToken = memberService.login(LoginMemberRequest.builder().email("email@email.com").password("password").build());
        String object = objectMapper.writeValueAsString(TokenRequest.builder()
                .accessToken(userToken.getAccessToken())
                .refreshToken(userToken.getRefreshToken())
                .build());
        //when
        ResultActions actions = mockMvc.perform(post("/gdsc/reissue")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("members/reissue",
                        preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("accessToken").description("accessToken"),
                                fieldWithPath("refreshToken").description("refreshToken")
                        ),
                        responseFields(
                                fieldWithPath("data.grantType").description("grantType"),
                                fieldWithPath("data.accessToken").description("accessToken"),
                                fieldWithPath("data.refreshToken").description("refreshToken"),
                                fieldWithPath("data.accessTokenExpireDate").description("accessToken 만료시간")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @Test
    void logout() throws Exception {
        //given
        TokenDto userToken = memberService.login(LoginMemberRequest.builder().email("email@email.com").password("password").build());
        String object = objectMapper.writeValueAsString(TokenRequest.builder()
                .accessToken(userToken.getAccessToken())
                .refreshToken(userToken.getRefreshToken())
                .build());

        ResultActions actions = mockMvc.perform(delete("/gdsc/logout")
                .header("Authorization", "Bearer " + userToken.getAccessToken())
                .content(object)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andDo(print())
                .andDo(document("members/logout",
                        preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("멤버의 Access Token")),
                        requestFields(
                                fieldWithPath("accessToken").description("accessToken"),
                                fieldWithPath("refreshToken").description("refreshToken")
                        )
                ))
                .andExpect(status().isNoContent());
    }

    @Test
    void memberInfo() throws Exception {
        //given
        ResultActions actions = mockMvc.perform(get("/gdsc/me")
                .header("Authorization", "Bearer " + token.getAccessToken()));
        //then
        //when
        actions
                .andDo(print())
                .andDo(document("members/me",
                        preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("멤버의 Access Token")),
                        responseFields(
                                fieldWithPath("data.id").description("멤버 고유번호"),
                                fieldWithPath("data.email").description("멤버 이메일"),
                                fieldWithPath("data.name").description("멤버 이름"),
                                fieldWithPath("data.part").description("멤버 파트"),
                                fieldWithPath("data.role").description("멤버 role")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void updateMember() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateMemberRequest.builder()
                .name("test_name")
                .part(Part.CORE)
                .description("hi hello")
                .build());

        //when
        ResultActions actions = mockMvc.perform(patch("/gdsc/me")
                .content(object)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("members/update",
                        preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("멤버의 Access Token")),
                        requestFields(
                                fieldWithPath("name").description("멤버 이름"),
                                fieldWithPath("part").description("멤버 파트"),
                                fieldWithPath("description").description("멤버 설명")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void updateMemberPassword() throws Exception {
        //given
        String object = objectMapper.writeValueAsString(UpdateMemberPasswordRequest.builder()
                .password("new_password")
                .build());

        //when
        ResultActions actions = mockMvc.perform(patch("/gdsc/me/password")
                .content(object)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andDo(document("members/updatePassword",
                        preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("멤버의 Access Token")),
                        requestFields(
                                fieldWithPath("password").description("멤버 비밀번호")
                        )
                ))
                .andExpect(status().isOk());
    }
}