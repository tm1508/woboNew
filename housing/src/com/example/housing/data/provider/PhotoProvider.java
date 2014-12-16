package com.example.housing.data.provider;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;

public class PhotoProvider extends BaseProvider<Photo> {

	@Override
	protected Class<Photo> getEntityClass() {
		return Photo.class;
	}
	
	public Photo findById(Integer id) {
		return (Photo) super.find(id);
	}

}
