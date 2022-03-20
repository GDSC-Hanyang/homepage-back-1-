package com.gdschanyang.homepage.advice.exception;

import com.gdschanyang.homepage.advice.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundEntityException extends BusinessException {
    public NotFoundEntityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
