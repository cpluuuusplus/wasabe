package bdd;

import java.net.UnknownHostException;

import com.ensai.pfe.wasabe.server.meteo.main.CurrentWeatherRecord;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class DAOCurrentWeather {
	
	
	
	
	
	public static void insertCurrentWeatherRecord(CurrentWeatherRecord cwr) throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		
		DB db = mongoClient.getDB( "historic" );
		DBCollection coll = db.getCollection("weather");
		
		BasicDBObject doc = new BasicDBObject("date", cwr.getDate())
		.append("day", cwr.getDate().getDay())
        .append("month", cwr.getDate().getMonth())
        .append("hour", cwr.getDate().getHours())
        .append("minute", cwr.getDate().getMinutes())
        .append("cityName", cwr.getCityName())
        .append("temp", cwr.getTemp())
        .append("humidity", cwr.getHumidity())
        .append("rain1h", cwr.getRain1h())
        .append("rain3h", cwr.getRain3h())
        .append("snow1h",cwr.getSnow1h())
        .append("snow3h", cwr.getSnow3h())
        .append("windspeed", cwr.getWindSpeed())
        .append("percentOfClouds", cwr.getPercentOfClouds())
        .append("isDay", cwr.isDay());
		
		coll.insert(doc);
		
		
	}
	
	
	
	
	
	
	
}
