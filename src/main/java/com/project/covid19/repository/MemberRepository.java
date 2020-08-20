package com.project.covid19.repository;

import com.project.covid19.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository <Member, Long> {
    public List<Member> findById(String id);
}
