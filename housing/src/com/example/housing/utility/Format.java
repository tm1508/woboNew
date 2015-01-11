package com.example.housing.utility;

public class Format {
	
	/*public static void main(String args[]){
		float p =(float) 22;

		String a = String.valueOf(p);

		char oldChar = '.';
		char newChar = ',';
		String p2 = stringFormat(p);
		System.out.println(p2+" "+a+"       "+a.indexOf('.') + (floatFormat(p2)));
	}*/
	
	public  String stringFormat(Float f){
		String s = String.valueOf(f);
		char oldChar = '.';
		char newChar = ',';
		s.replace(oldChar, newChar);
		return 	s.replace(oldChar, newChar);
		
	}
	
	public  Float floatFormat(String s){
		if(!s.isEmpty()){
		float f = Float.parseFloat(s.replace(",", "."));
		return f;
		}else{
			return (float) 0.0;
		}
	}
	
}
