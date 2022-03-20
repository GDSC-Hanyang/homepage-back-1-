package com.gdschanyang.homepage.service.common;

import org.springframework.mail.SimpleMailMessage;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
public interface EmailService {
    void sendEmail(SimpleMailMessage email);
}
