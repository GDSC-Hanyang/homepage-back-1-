package com.gdschanyang.homepage.domain.member;

import com.gdschanyang.homepage.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailToken extends BaseEntity {
    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;	//토큰 만료 시간

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;
    private LocalDateTime expirationDate; // 만료 시간
    private boolean expired; // 만료 여부
    private Long memberId; // Member 의 PK 값

    // 이메일 인증 토큰 생성
    public static EmailToken createEmailToken(Long memberId){
        EmailToken emailToken = new EmailToken();
        emailToken.expirationDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE); // 5분후 만료
        emailToken.memberId = memberId;
        emailToken.expired = false;
        return emailToken;
    }

    // 토큰 사용으로 인한 만료
    public void useToken(){
        expired = true;
    }
}
