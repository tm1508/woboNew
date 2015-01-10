package com.example.housing.utility;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.vaadin.ui.Upload.Receiver;


public class PhotoUploader implements Receiver {

	final String path = "C:\\temp\\";
	private String filePath;
	private byte[] pictureData;
	private int counter;
	
	/**
	* return an OutputStream that simply counts lineends
	*/
	@Override
	public OutputStream receiveUpload(String filename, String MIMEType) {
		
		filePath = path + filename;
		try {
			return new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public byte[] getPictureData() {
		
		return pictureData;
	    
	}
	
}