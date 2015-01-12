package com.example.housing.data.provider;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;

// TODO: Auto-generated Javadoc
/**
 * The Class PhotoProvider.
 */
public class PhotoProvider extends BaseProvider<Photo> {

	/* (non-Javadoc)
	 * @see com.example.housing.data.provider.BaseProvider#getEntityClass()
	 */
	@Override
	protected Class<Photo> getEntityClass() {
		return Photo.class;
	}
	
	public void addPhoto(Photo newPhoto) {
		
		//TODO: Foto verkleinern/Dateityp anpassen etc?
		
		if (!super.save(newPhoto)) {

			System.out.println("Neues Foto konnte nicht in die Datenbank gespeichert werden!");

		}

	}
	
	public boolean removePhoto(Photo photo) {
		
		return super.delete(photo.getIdPhoto());
		
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the photo
	 */
	public Photo findById(Integer id) {
		return (Photo) super.find(id);
	}

}
