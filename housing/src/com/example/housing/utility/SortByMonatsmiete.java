package com.example.housing.utility;

import java.util.Comparator;
import com.example.housing.data.model.Offer;

public class SortByMonatsmiete implements Comparator<Offer>{
	@Override
	public int compare(Offer arg0, Offer arg1) {
		Float arg0Price = new Float(arg0.getPrice());
		Float arg1Price = new Float(arg1.getPrice());
		return arg0Price.compareTo(arg1Price);
	}
}
