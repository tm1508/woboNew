package com.example.housing.utility;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

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
	 * @param d the d
	 * @return the string
	 */
	public String dateFormat(Date d) {
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
		String dateS = sd.format(d);
		return dateS;
	}

	public String stringFormat (float f) {
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
	public float floatFormat(String s) {
		if (!s.isEmpty()) {
			try{float f = Float.parseFloat(s.replace(",", "."));
			return f;}
			catch(NumberFormatException nfe){
				//TODO
				Notification not = new Notification("Bitte überprüfen Sie Ihre Eingaben.");
				not.setDelayMsec(300);
				not.setStyleName("warning");
				not.setIcon(FontAwesome.EXCLAMATION_TRIANGLE);
				not.show(Page.getCurrent()); 
				return (float) 0.0;
			}
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
	public String stringEuro(float f) {
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
