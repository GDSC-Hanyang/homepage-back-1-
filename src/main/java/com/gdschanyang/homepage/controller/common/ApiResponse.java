package com.gdschanyang.homepage.controller.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Getter
@Setter
@RequiredArgsConstructor
public final class ApiResponse<T>{
    private final T data;
}
