package com.nhnent.edu.spring_security.service;

import com.nhnent.edu.spring_security.entity.Member;
import com.nhnent.edu.spring_security.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = null;
        List<GrantedAuthority> authorities = new ArrayList<>();

        Optional<Member> member = memberRepository.findById(username);
        if (!member.isPresent()) {
            throw new UsernameNotFoundException("Not found member : " + username);
        }

        password = member.get().getPassword();
        authorities.add(new SimpleGrantedAuthority(member.get().getAuthority().getAuthority()));

        return new User(username, password, authorities);
    }

}
