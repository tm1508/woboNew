package com.example.housing;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class UploadTest.
 */
public class UploadTest extends VerticalLayout implements Receiver, SucceededListener, View
{

    /** The uploader. */
    private Upload uploader = new Upload ( "Foto hochladen", this );
    
    /** The tmp file. */
    ByteArrayOutputStream tmpFile = null;

    /**
     * Instantiates a new upload test.
     */
    public UploadTest ()
    {
        super();
        setMargin ( false );
        this.addComponent(uploader);
        uploader.addListener ( ( Upload.SucceededListener ) this );
        
      
    }

    /* (non-Javadoc)
     * @see com.vaadin.ui.Upload.Receiver#receiveUpload(java.lang.String, java.lang.String)
     */
    public OutputStream receiveUpload ( String filename, String MIMEType )
    {
        try
        {
            tmpFile = new ByteArrayOutputStream();
            return tmpFile;
        }
        catch ( Exception ex )
        {
            ex.printStackTrace ();
            throw new RuntimeException ( ex );
        }
    }

    /* (non-Javadoc)
     * @see com.vaadin.ui.Upload.SucceededListener#uploadSucceeded(com.vaadin.ui.Upload.SucceededEvent)
     */
    public void uploadSucceeded ( SucceededEvent event )
    {
    	System.out.println("Bild wurde hochgeladen");
        if ( tmpFile != null)
        {
            //TODO Bild in die Datenbank abspeichern, Byte Array bekommt man mit tmpFile.toByteArray();
        	String encodedImage = this.getImage(tmpFile.toByteArray());
        	 Label image = new Label("Dieses Bild wurde hochgeladen:"
                             + "\n"
                             + "\n<img  class='bild' src='data:image/jpg;base64,"+encodedImage+"' alt='IMG DESC'>");
             image.setContentMode(ContentMode.HTML);
        
        
        this.addComponent(image);
        }
    }
    
    /**
     * Gets the image.
     *
     * @param bytes the bytes
     * @return the image
     */
    public String getImage(byte[] bytes) {
		String encodedImage = "";
		encodedImage = Base64.encode(bytes).toString();
		return encodedImage;
	}
    
    /**
     * Upload failed.
     *
     * @param event the event
     */
    public void uploadFailed(FailedEvent event){
    	System.out.println("failed");
    }
    

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	

}