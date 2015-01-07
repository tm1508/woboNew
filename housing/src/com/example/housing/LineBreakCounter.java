package com.example.housing;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.vaadin.ui.Upload.Receiver;


public class LineBreakCounter implements Receiver {
	        private int counter;
	        private int total;
	        private boolean sleep;
	        public byte[] picture;
	
	        /**
	         * return an OutputStream that simply counts lineends
	         */
	        @Override
	        public OutputStream receiveUpload(final String filename,
	                final String MIMEType) {
	            counter = 0;
	            total = 0;

	            return new OutputStream() {
	                private static final int searchedByte = '\n';
	                
	                
	            
	                @Override
	                public void write(final int b) throws IOException {
	                	//String help = this.
	                	//picture=help.getBytes();
	                    total++;
	                    if (b == searchedByte) {
	                        counter++;
	                    }
	                    if (sleep && total % 1000 == 0) {
	                        try {
	                            Thread.sleep(100);
	                        } catch (final InterruptedException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                    }
	                };
	            
	           /*return new ByteArrayOutputStream() {
                private static final int searchedByte = '\n';
                @Override
                public void write(int b) {
                
                	picture = this.toByteArray();
                	System.out.println("ok");
                	
                	 total++;
	                    if (b == searchedByte) {
	                        counter++;
	                    }
	                    if (sleep && total % 1000 == 0) {
	                        try {
	                            Thread.sleep(100);
	                        } catch (final InterruptedException e) {
	                            e.printStackTrace();
	                        }
	                    }
                }
	            };*/
	        }
	 
	        public int getLineBreakCount() {
	            return counter;
	        }
	        
	        public byte[] getPicture() {
	            return picture;
	        }
	 
	        public void setSlow(final boolean value) {
	            sleep = value;
	        }
	 
	    }