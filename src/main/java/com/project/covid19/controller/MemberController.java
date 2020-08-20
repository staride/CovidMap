package com.project.covid19.controller;

import com.project.covid19.Entity.Member;
import com.project.covid19.Util.ValidatedUtil;
import com.project.covid19.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class MemberController {

    @Autowired
    MemberService service;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/check/{id}")
    public ResponseEntity<String> checkId(@PathVariable String id){
        log.info("checkid() : " + id);

        String message = null;

        if(!(id == null || id.trim().isEmpty())){
            if(service.checkId(id)){
                message = "Success";
            }else{
                message = "Fail";
            }

            return new ResponseEntity<String>(message, HttpStatus.OK);
        }

        message = "Fail";
        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody @Validated Member member){
        log.info("register() : data - " + member);
        String message = null;

        boolean isvalid = ValidatedUtil.validateRegisterMemberInfo(member);

        if(isvalid){

            member.setPassword(encoder.encode(member.getPassword()));

            if(service.register(member)){
                message = "Success";
            }else{
                message = "Fail";
            }

            return new ResponseEntity<String>(message, HttpStatus.OK);
        }

        message = "Fail";
        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }
}
