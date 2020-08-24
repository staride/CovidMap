package com.project.covid19.service;

import com.project.covid19.entity.Member;

public interface MemberService {
    public boolean checkId(String id);
    public boolean register(Member member);
    public Member getLoginInfo(String id);
}
