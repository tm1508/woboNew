package com.example.housing.utility;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Format {

	/*
	 * public static void main(String args[]){ float p =(float) 22;
	 * 
	 * String a = String.valueOf(p);
	 * 
	 * char oldChar = '.'; char newChar = ','; String p2 = stringFormat(p);
	 * System.out.println(p2+" "+a+"       "+a.indexOf('.') +
	 * (floatFormat(p2))); }
	 */
	public String dateFormat(Date d) {
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
		String dateS = sd.format(d);
		return dateS;
	}

	public String stringFormat(Float f) {
		String s = String.valueOf(f);
		char oldChar = '.';
		char newChar = ',';
		s.replace(oldChar, newChar);
		return s.replace(oldChar, newChar);

	}

	public Float floatFormat(String s) {
		if (!s.isEmpty()) {
			float f = Float.parseFloat(s.replace(",", "."));
			return f;
		} else {
			return (float) 0.0;
		}
	}

	public String stringEuro(Float f) {
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
