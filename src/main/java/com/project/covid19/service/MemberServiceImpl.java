package com.project.covid19.service;

import com.project.covid19.Entity.Member;
import com.project.covid19.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository repo;

    @Override
    public boolean checkId(String id) {
        return repo.findById(id).isEmpty() ? true : false ;
    }

    @Override
    public boolean register(Member member) {
        Member result = repo.save(member);
        return result == null ? false : true;
    }
}
