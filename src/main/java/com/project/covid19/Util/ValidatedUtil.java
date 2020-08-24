package com.project.covid19.Util;

import com.project.covid19.entity.Board;
import com.project.covid19.entity.Member;

public class ValidatedUtil {

    public static boolean validateRegisterMemberInfo(Member member){
        boolean result = false;

        if(member != null && !Util.isEmptyString(member.getId()) &&
           !Util.isEmptyString(member.getPassword()) &&
           !Util.isEmptyString(member.getNickName()) &&
           !Util.isEmptyString(member.getPhone()) &&
           !Util.isEmptyString(member.getEmail())){

            result = true;
        }

        return result;
    }

    public static boolean validateRegisterBoardInfo(Board board){
        boolean result = false;

        if(board != null && !Util.isEmptyString(board.getTitle()) &&
           !Util.isEmptyString(board.getContents()) &&
           !Util.isEmptyString(board.getWriter())){

            result = true;
        }

        return result;
    }

}
