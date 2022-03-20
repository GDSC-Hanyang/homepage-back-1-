package com.gdschanyang.homepage.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
public interface EmailTokenRepository extends JpaRepository<EmailToken,String> {
    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId, LocalDateTime now, boolean expired);
}
