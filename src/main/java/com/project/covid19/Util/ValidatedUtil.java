package com.project.covid19.Util;

import com.project.covid19.Entity.Member;
import org.springframework.validation.annotation.Validated;

public class ValidatedUtil {

    public static boolean validateRegisterMemberInfo(Member member){
        boolean result = false;

        if(!ValidatedUtil.isEmptyString(member.getId()) &&
           !ValidatedUtil.isEmptyString(member.getPassword()) &&
           !ValidatedUtil.isEmptyString(member.getNickName()) &&
           !ValidatedUtil.isEmptyString(member.getPhone()) &&
           !ValidatedUtil.isEmptyString(member.getEmail())){

            result = true;
        }

        return result;
    }

    public static boolean isEmptyString(String value){

        boolean result = false;

        if(value == null || value.trim().equals("")){
            result = true;
        }

        return result;
    }

}
