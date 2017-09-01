package com.ensai.pfe.wasabe.server.simulation;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StringToServer {
	
	
	public StringToServer(){}

	public static String stringToServer(String json) throws IOException {
		String url = "http://localhost:8080/Wasabe-Server/deviceinfo";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(json);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + json);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		onPostExecute(response.toString());
		System.out.println(response.toString());
		return response.toString();
	}

	public static void onPostExecute(String resultS) {

		if (resultS.isEmpty()) {
			// System.out.println("Donn�es enregistr�es");
			// Il n'y a rien dans le r�sultat
			// tv.setText("R�ponse vide re�ue !");

		} else {
			if (resultS.contains("identifiant")) {
				// tv.setText("Inscription effectu�e");
				updateDeviceInfoFromString(resultS);
			} else if (resultS.contains("tempsTotal")) {
				// tv.setText("Itin�raire re�u");
				populateItineraireTableFromString(resultS);
			} else if (resultS.contains("pasSurPeriph")) {
				// tv.setText("Vous n'�tes pas sur le p�riph�rique");
			}
		}
	}

	
	private static void populateItineraireTableFromString(String resultS) {
		// TODO Verifier le type 
		

	}

	private static void updateDeviceInfoFromString(String resultS) {
		// TODO Auto-generated method stub

	}
}
