package com.cs.BatchAutoGenerateParameter.utils;


public class StringEDCUtils {
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static String shorten(String text, int limit) {
        if (text == null) return "";
        if (text.length() > limit) {
            return text.substring(0, limit + 1) + "...";
        }
        return text;
    }
}
