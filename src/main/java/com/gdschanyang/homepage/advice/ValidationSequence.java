package com.gdschanyang.homepage.advice;

import com.gdschanyang.homepage.advice.ValidationGroups.*;
import javax.validation.GroupSequence;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@GroupSequence({NotEmptyGroup.class, SizeCheckGroup.class, PatternCheckGroup.class })
public interface ValidationSequence {
}