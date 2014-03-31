package com.teambition.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * User: dongwq
 * Date: 2014/02/15
 * Time: 6:04 PM
 */
public class PinyinUtil {
    /**
     * 汉字转换位汉语拼音首字母，英文字符不变 , 并转换成大写
     *
     * @param chString 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chString) {

        if (StringUtil.isBlank(chString)) return "";

        String pinyinName = "";
        chString = chString.replaceAll("\\p{P}", "");
        char[] nameChar = chString.toCharArray();

        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aNameChar : nameChar) {
            if (aNameChar > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(aNameChar, defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += aNameChar;
            }
        }

        String str = "";
        if (StringUtil.isNotBlank(pinyinName)) {
            str = pinyinName.substring(0, 1);
            int cp = pinyinName.codePointAt(0);
            if (Character.isLowerCase(cp)) {
                str = str.toUpperCase();
            }
        }

        return str;
    }

    /**
     * 汉字转换位汉语拼音，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        String pinyinName = "";

        if (StringUtil.isBlank(chines)) {
            return "";
        }

        chines = chines.replaceAll("\\p{P}", "");

        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for (char aNameChar : nameChar) {
            if (aNameChar > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(aNameChar, defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += aNameChar;
            }
        }
        return pinyinName;
    }
}
