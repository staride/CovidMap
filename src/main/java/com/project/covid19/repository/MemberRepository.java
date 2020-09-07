package com.project.covid19.repository;

import com.project.covid19.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository <Member, Long> {
    public List<Member> findById(String id);
    public List<Member> findByNickName(String nickName);
}
