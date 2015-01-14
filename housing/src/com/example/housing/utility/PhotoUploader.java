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


// TODO: Auto-generated Javadoc
/**
 * The Class PhotoUploader.
 */
public class PhotoUploader implements Receiver {

	/** The path. */
	final String path = "C:\\temp\\";
	
	/** The file path. */
	private String filePath;
	
	/** The picture data. */
	private byte[] pictureData;
	
	/** The counter. */
	private int counter;
	
	/**
	 * return an OutputStream that simply counts lineends.
	 *
	 * @param filename the filename
	 * @param MIMEType the MIME type
	 * @return the output stream
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
	
	/**
	 * Gets the picture data.
	 *
	 * @return the picture data
	 */
	public byte[] getPictureData() {
		
		return pictureData;
	    
	}
	
}