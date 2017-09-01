package com.ensai.pfe.wasabe.server.metier;

public class Coord {

	// Attributs
	private double longitude;
	private double latitude;

	// Constructeurs
	public Coord(double latitude, double longitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	// Getters, Setters
	public double getLong() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLat() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public int hashCode() {
		final int prime = 7;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coord other = (Coord) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	// Méthodes

	public String toString() {
		return "(" + (longitude+"").substring(0, 6) + "," + (latitude+"").substring(0, 6) + ")";
	}
}
