package com.teambition.util;

import android.text.TextUtils;

import java.util.List;

/**
 * Comment: ??
 * User: DongWQ
 * Date: 13-10-4，10:07
 *
 * @version 0.1
 */
public class StringUtil {

    public static boolean isNotBlank(String str) {
        if (TextUtils.isEmpty(str)) return false;
        return !TextUtils.isEmpty(str.trim());
    }

    public static boolean isBlank(String str) {
        return !StringUtil.isNotBlank(str);
    }

    public static boolean isNotEmpty(List collection) {
        return collection != null && !collection.isEmpty();
    }
}
