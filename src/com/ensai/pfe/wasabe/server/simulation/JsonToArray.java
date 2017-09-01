package com.ensai.pfe.wasabe.server.simulation;

import java.io.IOException;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ensai.pfe.wasabe.server.dao.DAOTroncon;

public class JsonToArray {

	public static double[][][] data; // stock all coordinate data in this array

	static {
		try {

			String in = DAOTroncon.readJsonFromUrl("latlon");

			// Parse the json file
			JSONObject obj = new JSONObject(in);

			// "features" is an array of size 2: outer and inner peripheral
			JSONArray array = (JSONArray) obj.get("features");
			int arraySize = array.length();

			// 3-D array to store all coordinates
			data = new double[arraySize][][];

			for (int i = 0; i < arraySize; i++) {

				// get the coordinates
				JSONArray coordinatesArray = (JSONArray) ((JSONObject) ((JSONObject) array
						.get(i)).get("geometry")).get("coordinates");

				int coordinatesArraySize = coordinatesArray.length();
				data[i] = new double[coordinatesArraySize][2];

				// transform String format coordinates to double and stock in
				// the 3-D double array
				String[] items = coordinatesArray.toString()
						.replaceAll("\\[|\\]", "").split(",");

				for (int j = 0; j < coordinatesArraySize; j++) {
					data[i][j] = new double[] {
							Double.parseDouble(items[j * 2]),
							Double.parseDouble(items[j * 2 + 1]) };
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// test
	public static void main(String[] args) {
//		System.out.println(Arrays.deepToString(data[0]));
//		System.out.println();
//		System.out.println(Arrays.deepToString(data[1]));
	}
}