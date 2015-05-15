package com.example.housing.utility;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class Format.
 */
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
	/**
	 * Date format.
	 *
	 * @param start the d
	 * @return the string
	 */
	public static String dateFormat(Date start) throws NullPointerException {
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
	
	/**
	 * Float format.
	 *
	 * @param s the s
	 * @return the float
	 */
	public static float floatFormat(String s) throws NumberFormatException {
		
		if (!s.isEmpty()) {
			
			float f = Float.parseFloat(s.replace(",", "."));
			return f;
			
		} else {
			
			return (float) 0.0;
			
		}
	}

	/**
	 * String euro.
	 *
	 * @param f the f
	 * @return the string
	 */
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
