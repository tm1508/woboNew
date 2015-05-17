package com.example.housing.utility;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Format {

	public static String dateFormat(Date start)  throws IllegalArgumentException {
		if(start == null) {
			throw new NullPointerException("Keine Eingabe in Datumsfeld");
		}
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
		String dateS = sd.format(start);
		return dateS;
	}

	public static String stringFormat (float f) {
		String s = String.valueOf(f);
		char oldChar = '.';
        char newChar = ',';
        String result = s.replace(oldChar, newChar);
		return result;
	}
	
	public static float floatFormat(String s) throws NumberFormatException {
		if (!s.isEmpty()) {
			float f = Float.parseFloat(s.replace(",", "."));
			return f;
		} else {
			return (float) 0.0;
		}
	}

	public static String euroFormat(float f) {
		String s = String.valueOf(f);
		String[] a = s.split(Pattern.quote("."));
		if (a[1].length() == 1){
			s = s + "0";}
		char oldChar = '.';
		char newChar = ',';
		s.replace(oldChar, newChar);
		return s.replace(oldChar, newChar);
	}
}
