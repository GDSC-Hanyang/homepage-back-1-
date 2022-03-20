package com.gdschanyang.homepage.service.member;

import com.gdschanyang.homepage.domain.member.EmailToken;
import com.gdschanyang.homepage.domain.member.EmailTokenRepository;
import com.gdschanyang.homepage.domain.member.exception.CNotFoundEmailTokenException;
import com.gdschanyang.homepage.service.common.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Service
@RequiredArgsConstructor
public class EmailTokenServiceImpl implements EmailTokenService {
    private final EmailTokenRepository emailTokenRepository;
    private final EmailServiceImpl emailService;

    @Value("${spring.host}")
    private String emailConfirmHost;

    // 이메일 발송 및 이메일 인증 토큰 생성
    public String createEmailToken(Long memberId, String receiverEmail) {
        EmailToken emailConfirmationToken = EmailToken.createEmailToken(memberId);
        emailTokenRepository.save(emailConfirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText(emailConfirmHost + "/email?token="+emailConfirmationToken.getId());
        emailService.sendEmail(mailMessage);

        return emailConfirmationToken.getId();
    }

    // 유효한 토큰 가져오기
    public EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId) {
        return emailTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(),false)
                .orElseThrow(CNotFoundEmailTokenException::new);
    };
}
