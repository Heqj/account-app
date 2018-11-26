package com.hqj.account.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class StringUtil {

    public static boolean isEmpty(String str) {
        if(str == null) {
            return true;
        }
        if(str.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isPhoneNumber(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        boolean flag;
        if(phone.length() != 11){
            flag = false;
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            flag = m.matches();
        }
        return flag;
    }
}
