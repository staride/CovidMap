package com.project.covid19.util;

import com.project.covid19.entity.Board;
import com.project.covid19.entity.Member;

public class ValidatedUtil {

    public static boolean validateRegisterMemberInfo(Member member){
        boolean result = false;

        if(member != null && !Util.isEmptyString(member.getId().trim()) &&
           !Util.isEmptyString(member.getPassword().trim()) &&
           !Util.isEmptyString(member.getNickName().trim()) &&
           !Util.isEmptyString(member.getPhone().trim()) &&
           !Util.isEmptyString(member.getEmail().trim())){

            result = true;
        }

        return result;
    }

    public static boolean validateRegisterBoardInfo(Board board){
        boolean result = false;

        if(board != null && !Util.isEmptyString(board.getTitle().trim()) &&
           !Util.isEmptyString(board.getContents().trim()) &&
           !Util.isEmptyString(board.getWriter().trim())){

            result = true;
        }

        return result;
    }

    public static boolean validateModifyBoardInfo(Board board){
        boolean result = false;

        if(board != null && !Util.isEmptyString(board.getTitle().trim()) && !Util.isEmptyString(board.getContents().trim())){
            result = true;
        }

        return result;
    }
}
