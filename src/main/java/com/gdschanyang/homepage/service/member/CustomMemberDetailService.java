package com.gdschanyang.homepage.service.member;

import com.gdschanyang.homepage.domain.member.MemberPrincipal;
import com.gdschanyang.homepage.domain.member.MemberRepository;
import com.gdschanyang.homepage.domain.member.exception.CNotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Created by ParkSuHo on 2022/03/20.
 */
@Service
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return new MemberPrincipal(memberRepository.findById(Long.valueOf(id)).orElseThrow(CNotFoundMemberException::new));
    }
}