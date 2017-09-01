package bdd;

import java.io.IOException;

import org.json.JSONException;

import com.ensai.pfe.wasabe.server.meteo.main.CurrentWeather;
import com.ensai.pfe.wasabe.server.meteo.main.CurrentWeatherRecord;
import com.ensai.pfe.wasabe.server.meteo.main.OpenWeatherMap;

public class RecordingCurrentWeather {
	public static final int delaiMS = 180000;
	
	public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		OpenWeatherMap owm = new OpenWeatherMap("c8d62752e7a98c7316a216ad78689472");

		while (true) {
			
			CurrentWeather cw = owm.currentWeatherByCityName("Rennes");
			System.out.println(cw);
			CurrentWeatherRecord cwr = new CurrentWeatherRecord(cw);
			System.out.println(cwr);
			DAOCurrentWeather.insertCurrentWeatherRecord(cwr);
			Thread.sleep(delaiMS);
		}

	}

}
