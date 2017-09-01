package com.ensai.pfe.wasabe.server.metier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceInfo {

	// Attributes

	@XmlElement
	private Double tempsReleve; // Temps UNIX associ� � ce deviceInfo
	@XmlElement
	private Double latitude; // Mesure de latitude
	@XmlElement
	private Double longitude; // Mesure de longitude
	@XmlElement
	private Double precision; // Pr�cision de la mesure de longitude/latitude
	@XmlElement
	private String id; // Identifiant du device (attribu� par le serveur
	@XmlElement
	private String destination; // nom de porte auquel l'utilisateur veut aller

	private double angle; 


	private double speed=-1.0;

	// Constructor

	public DeviceInfo() {
		id = "";
	}

	public DeviceInfo(Double temps, Double latitude, Double longitude,
			Double precision, String id, String destination) {
		this.tempsReleve = temps;
		this.latitude = latitude;

		this.longitude = longitude;
		this.precision = precision;
		this.id = id;
		this.destination = destination;
		 // System.out.println("DeviceInfo:: DeviceInfo initialis�e :" + temps
		//		+ " " + latitude + " " + longitude + " " + precision + " " + id
		//		+ " " + destination);
	}

	public DeviceInfo(Double temps, Double latitude, Double longitude,
			Double precision, String id, String destination, double vitesse) {
		this.tempsReleve = temps;
		this.latitude = latitude;

		this.longitude = longitude;
		this.precision = precision;
		this.id = id;
		this.destination = destination;
		this.speed = vitesse;
		

	}
	
	
	public DeviceInfo(String exception) {
		System.out.println("DeviceInfo:: Exception g�n�r�e");
	}

	// Getters, Setters

	public Double getTempsReleve() {
		return tempsReleve;
	}

	public void setTempsReleve(Double temps) {
		this.tempsReleve = temps;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	public String getIdDevice() {
		return id;
	}

	public void setIdDevice(String idDevice) {
		this.id = idDevice;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String toString() {
		return "{idDevice : " + id + " ; lon:" + longitude + " ; lat"
				+ latitude + " ; precision: " + precision + " destination: "
				+ destination + "vitesse" + getSpeed() +" }";
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	
}
