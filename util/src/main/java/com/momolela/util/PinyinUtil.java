package com.momolela.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

        System.out.println(PinyinHelper.toHanYuPinyinString("java律师123", format, "-", true)); // javalu:4-shi1123
        System.out.println(PinyinHelper.toHanYuPinyinString("java律师123", format, "", true)); // javalu:4shi1123
        System.out.println(PinyinHelper.toHanYuPinyinString("java律师123", format, "", false)); // lu:4shi1
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE); // 控制是否大小写
        System.out.println(PinyinHelper.toHanYuPinyinString("java律师123", format, "", false)); // LU:4SHI1
        format.setVCharType(HanyuPinyinVCharType.WITH_V); // 控制 u: 是否要转变成 v
        System.out.println(PinyinHelper.toHanYuPinyinString("java律师123", format, "", false)); // LV4SHI1
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 控制是否要音调
        System.out.println(PinyinHelper.toHanYuPinyinString("java律师123", format, "", false)); // LVSHI
    }
}
