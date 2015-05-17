package com.example.housing.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vaadin.tapio.googlemaps.client.LatLon;

public class MapAddressConverter {

	public static LatLon getLatLonFromAddress(String address) {
		try {
			URL url = new URL(
					"http://maps.googleapis.com/maps/api/geocode/xml?address="
							+ address);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String urlString = "";
			String current;
			while ((current = in.readLine()) != null) {
				urlString += current;
			}
			String[] array1 = urlString.split(Pattern.quote("<lat>"));
			String[] array2 = array1[1].split(Pattern.quote("</lat>"));
			String lat = array2[0];

			String[] array3 = urlString.split(Pattern.quote("<lng>"));
			String[] array4 = array3[1].split(Pattern.quote("</lng>"));
			String lon = array4[0];

			return new LatLon(Double.parseDouble(lat), Double.parseDouble(lon));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<String> getAddressFromLatLon(Double lat, Double lon) {
		try {
			URL url = new URL(
					"http://maps.googleapis.com/maps/api/geocode/xml?latlng="
							+ lat + "," + lon);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			}
			String output = "";
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db;
				db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setByteStream(connection.getInputStream());
				Document doc = db.parse(is);

				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
						"yes");
				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(doc), new StreamResult(
						writer));
				output = writer.getBuffer().toString().replaceAll("\n|\r", "");

				System.out.println(output);
			} catch (SAXException | ParserConfigurationException
					| TransformerException e) {
				e.printStackTrace();
			}

			String[] array1 = output
					.split(Pattern.quote("<formatted_address>"));
			String[] array2 = array1[1].split(Pattern
					.quote("</formatted_address>"));
			String address = array2[0];

			String[] array3 = address.split(Pattern.quote(","));
			String str = array3[0];
			String[] array4 = array3[1].split(Pattern.quote(" "));
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
