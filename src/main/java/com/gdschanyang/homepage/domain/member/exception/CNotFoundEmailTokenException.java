package com.gdschanyang.homepage.domain.member.exception;

import com.gdschanyang.homepage.advice.ErrorCode;
import com.gdschanyang.homepage.advice.exception.NotFoundEntityException;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
public class CNotFoundEmailTokenException extends NotFoundEntityException {
    public CNotFoundEmailTokenException() { super(ErrorCode.EMAIL_TOKEN_NOT_FOUND); }
}