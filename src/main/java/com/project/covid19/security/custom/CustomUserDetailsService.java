package com.project.covid19.security.custom;

import com.project.covid19.entity.Member;
import com.project.covid19.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.info("userName: " + username);

        Member member = repo.findById(username).get(0);

        log.info("member: " + member);

        return member == null ? null : new CustomUser(member);
    }
}
