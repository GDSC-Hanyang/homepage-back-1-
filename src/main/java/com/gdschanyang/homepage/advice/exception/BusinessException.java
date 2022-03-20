package com.gdschanyang.homepage.advice.exception;

import com.gdschanyang.homepage.advice.ErrorCode;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
