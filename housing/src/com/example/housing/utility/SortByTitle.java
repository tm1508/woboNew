package com.example.housing.utility;

import java.util.Comparator;
import com.example.housing.data.model.Offer;

public class SortByTitle implements Comparator<Offer>{
	@Override
	public int compare(Offer arg0, Offer arg1) {
		return arg0.getTitle().compareToIgnoreCase(arg1.getTitle());
	}
}
