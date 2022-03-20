package com.gdschanyang.homepage.domain.member.exception;

import com.gdschanyang.homepage.advice.ErrorCode;
import com.gdschanyang.homepage.advice.exception.ConflictException;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
public class CAlreadyJoinMemberException extends ConflictException {
    public CAlreadyJoinMemberException() { super(ErrorCode.ALREADY_JOIN_MEMBER); }
}
