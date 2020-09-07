package com.project.covid19.service;

import com.project.covid19.entity.Member;
import org.json.simple.parser.ParseException;

public interface MemberService {
    public boolean checkId(String id);
    public boolean register(Member member);
    public Member getLoginInfo(String id);
    public boolean updateUserCoordinate(String id, String data) throws ParseException;
    public boolean checkNickName(String nickName);
}
