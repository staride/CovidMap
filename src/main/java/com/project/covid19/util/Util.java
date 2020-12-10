package com.project.covid19.util;

import lombok.extern.java.Log;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

@Log
public class Util {
    public static String getBodyFromHttpRequest(HttpServletRequest request) throws Exception{

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    public static boolean isEmptyString(String value){

        boolean result = false;

        if(value == null || value.trim().equals("")){
            result = true;
        }

        return result;
    }

    public static boolean isContainsHour(String value){
        return Pattern.matches("^.*(1[0-9]|2[0-3])([:])+([0-5][0-9]).*$", value);
    }

    public static boolean isContainsDate(String value){
        return Pattern.matches("^.*([1-9]|1[0-2])월([ ])?([0-9]|1[0-9]|2[0-9]|3[0-1])일.*$", value) ||
                Pattern.matches("^.*(([1-9]|1[0-2])([/])+([ ])?([0-9]|1[0-9]|2[0-9]|3[0-1])+).*$", value) ||
                Pattern.matches("^.*(([1-9]|1[0-2])([.])+([ ])?([0-9]|1[0-9]|2[0-9]|3[0-1])+).*$", value);
    }
}
