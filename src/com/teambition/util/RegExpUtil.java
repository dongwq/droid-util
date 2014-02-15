package com.teambition.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: dongwq
 * Date: 2013/11/13
 * Time: 2:21 PM
 */
public class RegExpUtil {


    /**
     * 判断是否为邮箱
     */
    public static boolean isEmail(String strEmail) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9_-][\\w\\.-]*[a-zA-Z0-9_-]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
}
