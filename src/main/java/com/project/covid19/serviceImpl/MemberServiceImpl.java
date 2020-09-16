package com.project.covid19.serviceImpl;

import com.project.covid19.entity.Member;
import com.project.covid19.repository.MemberRepository;
import com.project.covid19.service.MemberService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository repo;

    @Override
    public boolean checkId(String id) {
        return repo.findById(id).isEmpty() ? true : false;
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

    @Override
    public boolean updateUserCoordinate(String id, String data) throws ParseException {
        List<Member> list = repo.findById(id);
        if(list != null && list.size() == 1){

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);

            if(obj instanceof JSONObject){
                JSONObject json = (JSONObject) obj;

                Member member = list.get(0);
                member.setPositionX((String)json.get("x"));
                member.setPositionY((String)json.get("y"));
                Member result = repo.save(member);

                if(member.getPositionX().equals(result.getPositionX()) && member.getPositionY().equals(result.getPositionY())){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean checkNickName(String nickName) {
        return repo.findByNickName(nickName).isEmpty() ? true : false;
    }
}
