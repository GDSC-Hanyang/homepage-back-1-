package com.gdschanyang.homepage.service.member;

import com.gdschanyang.homepage.domain.member.EmailToken;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
public interface EmailTokenService {
    String createEmailToken(Long memberId, String receiverEmail);
    EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId);
}
