package com.nhnent.edu.spring_security.service;

import com.nhnent.edu.spring_security.entity.Member;
import com.nhnent.edu.spring_security.repository.MemberRepository;
import com.nhnent.edu.spring_security.userdetail.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findById(username);
        if (!member.isPresent()) {
            throw new UsernameNotFoundException("Not found member : " + username);
        }

        String password = member.get().getPassword();
        String authority = member.get().getAuthority().getAuthority();

        // TODO : #5 실습 - UserDetails의 custom 구현체를 반환하세요.
        //        cf.) CustomUserDetails
        return new CustomUserDetails(username, password, authority);
    }

}
