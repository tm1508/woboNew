package com.example.housing.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.vaadin.tapio.googlemaps.client.LatLon;

public class MapAddressConverter {

	//TODO nur zum Testen!!! löschen
	/*public static void main(String[] args) {
		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/geocode/xml?address=Erzbergerstr+121+Karlsruhe");
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String urlString = "";
			String current;
			while ((current = in.readLine()) != null) {
				urlString += current;
			}
			System.out.println(urlString);
			String[] array1 = urlString.split( Pattern.quote( "<lat>" ) );
			System.out.println(array1[1]);
			String[] array2 = array1[1].split( Pattern.quote( "</lat>" ) );
			System.out.println(array2[0]);
			String lat = array2[0];
			
			String[] array3 = urlString.split( Pattern.quote( "<lng>" ) );
			System.out.println(array3[1]);
			String[] array4 = array3[1].split( Pattern.quote( "</lng>" ) );
			System.out.println(array4[0]);
			String lon = array4[0];
			
			new LatLon(Double.parseDouble(lat), Double.parseDouble(lon));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public static void main (String args[]){
		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/geocode/xml?latlng=49.0263200,8.3854400");
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String urlString = "";
			String current;
			while ((current = in.readLine()) != null) {
				urlString += current;
			}
			System.out.println(urlString);
			
			String[] array1 = urlString.split( Pattern.quote( "<formatted_address>" ) );
			String[] array2 = array1[1].split( Pattern.quote( "</formatted_address>" ) );
			String address = array2[0];
			
			String[] array3 = address.split( Pattern.quote( "," ) );
			String str = array3[0];
			String[] array4 = array3[1].split( Pattern.quote( " " ) );
			String plz = array4[1];
			String stadt = array4[2];
			
			System.out.println(str);
			System.out.println(plz);
			System.out.println(stadt);
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public LatLon getLatLonFromAddress(String address) {
		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/geocode/xml?address="+ address);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String urlString = "";
			String current;
			while ((current = in.readLine()) != null) {
				urlString += current;
			}
			String[] array1 = urlString.split( Pattern.quote( "<lat>" ) );
			String[] array2 = array1[1].split( Pattern.quote( "</lat>" ) );
			String lat = array2[0];
			
			String[] array3 = urlString.split( Pattern.quote( "<lng>" ) );
			String[] array4 = array3[1].split( Pattern.quote( "</lng>" ) );
			String lon = array4[0];
			
			return new LatLon(Double.parseDouble(lat), Double.parseDouble(lon));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getAddressFromLatLon(Double lat, Double lon) {
		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/geocode/xml?latlng="+ lat+","+lon);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String urlString = "";
			String current;
			while ((current = in.readLine()) != null) {
				urlString += current;
			}
			
			String[] array1 = urlString.split( Pattern.quote( "<formatted_address>" ) );
			String[] array2 = array1[1].split( Pattern.quote( "</formatted_address>" ) );
			String address = array2[0];
			
			String[] array3 = address.split( Pattern.quote( "," ) );
			String str = array3[0];
			String[] array4 = array3[1].split( Pattern.quote( " " ) );
			String plz = array4[1];
			String stadt = array4[2];
			
			List<String> l = new ArrayList<String>();
			l.add(str);
			l.add(plz);
			l.add(stadt);
			return l;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
