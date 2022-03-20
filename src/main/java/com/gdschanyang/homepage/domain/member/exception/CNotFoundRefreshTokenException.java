package com.gdschanyang.homepage.domain.member.exception;

import com.gdschanyang.homepage.advice.ErrorCode;
import com.gdschanyang.homepage.advice.exception.NotFoundEntityException;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
public class CNotFoundRefreshTokenException extends NotFoundEntityException {
    public CNotFoundRefreshTokenException() { super(ErrorCode.REFRESH_TOKEN_NOT_FOUND); }
}
