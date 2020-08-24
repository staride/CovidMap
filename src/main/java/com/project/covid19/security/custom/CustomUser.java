package com.project.covid19.security.custom;

import com.project.covid19.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUser extends User {
    private Member member;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(Member member) {
        super(member.getId(), member.getPassword(), new ArrayList<SimpleGrantedAuthority>());
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
