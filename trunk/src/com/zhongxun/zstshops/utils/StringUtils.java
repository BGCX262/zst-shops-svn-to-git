package com.zhongxun.zstshops.utils;

public class StringUtils {
	// GENERAL_PUNCTUATION 判断中文的“号

	// CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号

	// HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号

	public static boolean isChinese(char c) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

		|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

			return true;

		}

		return false;

	}

	/**
	 * 判断是否中文字符串
	 * 
	 * @param strName
	 */
	public static boolean isChinese(String strName) {

		char[] ch = strName.toCharArray();

		for (int i = 0; i < ch.length; i++) {

			char c = ch[i];

			if (isChinese(c) == false) {
				return false;

			}

		}

		return true;

	}
}
