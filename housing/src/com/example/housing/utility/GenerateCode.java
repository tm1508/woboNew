package com.example.housing.utility;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

public class GenerateCode {
	
	public static void main(String args[]){
		System.out.println(generateCode("test@web.de"));
	}
	
	public static String generateCode(String email){
		String codeText="";//verschlüsselter Code
		try {
		//1. Schlüssel erzeugen
			String keyText = "wohnungsboerse";//Schlüsseltext als String
			byte[] key = (keyText).getBytes("UTF-8");//Schlüssel-Text zu byteArray konvertieren
			MessageDigest sha = MessageDigest.getInstance("MD5");//Hash-Wert erzeugen
			key = sha.digest(key);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");//AES Schlüssel aus key erzeugen
		
		//2. E-Mail verschlüsseln (wurde als String übergeben)
			Cipher cipher = Cipher.getInstance("AES");//AES
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);//Schlüssel
			byte[] code = cipher.doFinal(email.getBytes());//E-Mail verschlüsseln
			BASE64Encoder encoder = new BASE64Encoder();//ByteArray zu lesbarem String konvertieren
			codeText = encoder.encode(code);
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		return codeText;
	}
}
