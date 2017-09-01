package com.ensai.pfe.wasabe.server.meteo.main;

import java.util.Date;

public class CurrentWeatherRecord {
	private String cityName;
	private Date date;
	private float percentOfClouds;
	private float temp;
	private float humidity;
	private float rain1h;
	private float rain3h;
	private float windSpeed;
	private float snow1h;
	private float snow3h;
	private boolean day;

	public CurrentWeatherRecord(CurrentWeather cw) {
		if (!cw.isValid()) {
			System.out.println("Reponse is inValid!");
		} else {

			this.cityName = cw.getCityName();
			this.date = cw.getDateTime();

			if (cw.hasCloudsInstance()) {
				this.percentOfClouds = cw.getCloudsInstance()
						.getPercentageOfClouds();
			}

			if (cw.hasMainInstance()) {
				this.temp = cw.getMainInstance().getTemperature();
				this.humidity = cw.getMainInstance().getHumidity();
			}

			if (cw.hasRainInstance()) {
				if(!Float.isNaN(cw.getRainInstance().getRain1h())){
					this.rain1h = cw.getRainInstance().getRain1h();
				}
				else{
					this.rain1h=0;
				}
				
				this.rain3h = cw.getRainInstance().getRain3h();
			} else {
				this.rain1h = 0;
				this.rain3h = 0;
			}

			if (cw.hasWindInstance()) {
				this.windSpeed = cw.getWindInstance().getWindSpeed();
			}

			if (cw.hasSnowInstance()) {
				if(!Float.isNaN(cw.getSnowInstance().getSnow1h())){
					this.snow1h = cw.getSnowInstance().getSnow1h();
				}
				else{
					this.snow1h=0;
				}
				this.snow3h = cw.getSnowInstance().getSnow3h();
			} else {
				this.snow1h = 0;
				this.snow3h = 0;
			}

			if (cw.hasSysInstance()) {
				if (cw.getDateTime()
						.before(cw.getSysInstance().getSunsetTime())
						&& cw.getDateTime().after(
								cw.getSysInstance().getSunriseTime())) {
					this.day = true;
				} else {
					this.day = false;
				}
			}

		}

	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getPercentOfClouds() {
		return percentOfClouds;
	}

	public void setPercentOfClouds(float percentOfClouds) {
		this.percentOfClouds = percentOfClouds;
	}

	public float getSnow1h() {
		return snow1h;
	}

	public void setSnow1h(float snow1h) {
		this.snow1h = snow1h;
	}

	public float getSnow3h() {
		return snow3h;
	}

	public void setSnow3h(float snow3h) {
		this.snow3h = snow3h;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getRain1h() {
		return rain1h;
	}

	public void setRain1h(float rain1h) {
		this.rain1h = rain1h;
	}

	public float getRain3h() {
		return rain3h;
	}

	public void setRain3h(float rain3h) {
		this.rain3h = rain3h;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public boolean isDay() {
		return day;
	}

	public void setDay(boolean day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "CurrentWeatherRecord [cityName=" + cityName + ", date=" + date
				+ ", percentOfClouds=" + percentOfClouds + ", temp=" + temp
				+ ", humidity=" + humidity + ", rain1h=" + rain1h + ", rain3h="
				+ rain3h + ", windSpeed=" + windSpeed + ", snow1h=" + snow1h
				+ ", snow3h=" + snow3h + ", day=" + day + "]";
	}

	

}
