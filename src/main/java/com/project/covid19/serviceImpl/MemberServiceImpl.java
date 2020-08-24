package com.project.covid19.serviceImpl;

import com.project.covid19.entity.Member;
import com.project.covid19.repository.MemberRepository;
import com.project.covid19.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

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

    @Override
    public Member getLoginInfo(String id) {
        List<Member> list = repo.findById(id);
        return list.isEmpty() ? null : list.get(0);
    }
}
