package com.nhnent.edu.spring_security.repository;

import com.nhnent.edu.spring_security.entity.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, String> {
    Optional<Member> findById(String name);

}
