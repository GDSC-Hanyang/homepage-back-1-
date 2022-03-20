package com.gdschanyang.homepage.advice.exception;

import com.gdschanyang.homepage.advice.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidException extends BusinessException{
    public InvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}