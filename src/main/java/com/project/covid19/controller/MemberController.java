package com.project.covid19.controller;

import com.project.covid19.entity.Member;
import com.project.covid19.Util.ValidatedUtil;
import com.project.covid19.service.MemberService;
import lombok.extern.java.Log;

import org.json.simple.parser.ParseException;
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

    @GetMapping("/checkid/{id}")
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

    @GetMapping("/checknick/{nickName}")
    public ResponseEntity<String> checkNickName(@PathVariable String nickName){
        log.info("checkNickName() : " + nickName);

        String message = null;

        if(!(nickName == null || nickName.trim().isEmpty())){
            if(service.checkNickName(nickName)){
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

    @GetMapping("/getLoginInfo/{id}")
    public ResponseEntity<Member> getLoginInfo(@PathVariable String id){

        log.info("getLoginInfo() : id - " + id);

        Member member = null;
        member = service.getLoginInfo(id);

        if(member != null){
            member.setPassword("");
            member.setRefreshToken("");
            return new ResponseEntity<Member>(member, HttpStatus.OK);
        }

        return new ResponseEntity<Member>(member, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/coordinate/{id}")
    public ResponseEntity<String> updateUserCoordinate(@PathVariable String id, @RequestBody String data) throws ParseException {
        log.info("updateUserCoordinate() : id - " + id + ", data - " + data);
        String message = "Fail";

        if(service.updateUserCoordinate(id, data)){
            message = "Success";
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }

        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }
}
