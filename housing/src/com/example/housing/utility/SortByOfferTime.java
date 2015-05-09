package com.example.housing.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.example.housing.data.model.Offer;

public class SortByOfferTime implements Comparator<Offer>{
	
	//TODO löschen, nur zum Testen
	static List<Offer> o;
	public static void main(String [] args){
		
		o = new ArrayList<Offer>();
		Offer aOffer = new Offer();
		aOffer.setTitle("a");
		aOffer.setOfferTime(new Date(2000, 1, 1));
		Offer bOffer = new Offer();
		bOffer.setTitle("b");
		bOffer.setOfferTime(new Date(2015, 1, 1));
		Offer cOffer = new Offer();
		cOffer.setTitle("c");
		cOffer.setOfferTime(new Date(1999, 1, 1));
		
		o.add(bOffer);
		o.add(cOffer);
		o.add(aOffer);
		
		
		Collections.sort(o, new SortByTitle());
		 System.out.println("Sortierung nach Fahrzeugmarke:");
		 Iterator<Offer> it = o.iterator();
		 while(it.hasNext()){
			 System.out.println(it.next().getOfferTime());
		 }
	}

	@Override
	public int compare(Offer arg0, Offer arg1) {
		// TODO Auto-generated method stub
		return arg0.getOfferTime().compareTo(arg1.getOfferTime());
	}

}
