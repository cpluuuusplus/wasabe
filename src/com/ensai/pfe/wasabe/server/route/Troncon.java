/**
 * 
 */
package com.ensai.pfe.wasabe.server.route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ensai.pfe.wasabe.server.metier.Device;
import com.ensai.pfe.wasabe.server.metier.DeviceInfo;

/**
 * @author ensai
 *
 */
public class Troncon {

	// Attributs
	private String idTroncon;
	private double angle;
	private double distance;
	private List<Device> devices = new ArrayList<Device>();
	private Intersection intersectionSuivanteSensHoraire;
	private Intersection intersectionPrecedenteSensHoraire;
	private double vitessemax;
	private double vitesseMoyenne;
	private double vitesseMediane;
	private double tempsDernierChangement;
	private double tempsEtatConstant;
	private String indicatif;


	/**
	 * 
	 * @param idTroncon identifiant en base du troncon
	 * @param angle angle en degres utilis� pour voir dans quel sens va le device
	 * @param distance La longueur du troncon en metres
	 * @param devices l'ensemble des devices pr�sents sur le troncon
	 * @param vitessemax la vitesse maximale en km/h
	 */
	public Troncon(String idTroncon, double angle, double distance,
			List<Device> devices, double vitessemax) {
		super();
		this.idTroncon = idTroncon;
		this.angle = angle;
		this.distance = distance;
		this.devices = devices;
		this.vitessemax = vitessemax;
		this.vitesseMoyenne = vitessemax;
		this.vitesseMediane = vitessemax;
		this.indicatif = "fluide";
		this.tempsEtatConstant = 0;
	}

	public String getIdTroncon() {
		return idTroncon;
	}

	public void setIdTroncon(String idTroncon) {
		this.idTroncon = idTroncon;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}


	public List<Device> getDevices() {
		return devices;
	}


	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}


	public double getVitessemax() {
		return vitessemax;
	}


	public void setVitessemax(double vitessemax) {
		this.vitessemax = vitessemax;
	}


	/**
	 * @return the intersectionPrecedenteSensHoraire
	 */
	public Intersection getIntersectionPrecedenteSensHoraire() {
		return intersectionPrecedenteSensHoraire;
	}

	/**
	 * @param intersectionPrecedenteSensHoraire the intersectionPrecedenteSensHoraire to set
	 */
	public void setIntersectionPrecedenteSensHoraire(
			Intersection intersectionPrecedenteSensHoraire) {
		this.intersectionPrecedenteSensHoraire = intersectionPrecedenteSensHoraire;
	}

	/**
	 * @return the intersectionSuivanteSensHoraire
	 */
	public Intersection getIntersectionSuivanteSensHoraire() {
		return intersectionSuivanteSensHoraire;
	}

	/**
	 * @param intersectionSuivanteSensHoraire the intersectionSuivanteSensHoraire to set
	 */
	public void setintersectionSuivanteSensHoraire(
			Intersection intersectionSuivanteSensHoraire) {
		this.intersectionSuivanteSensHoraire = intersectionSuivanteSensHoraire;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTroncon == null) ? 0 : idTroncon.hashCode());
		return result;
	}

	/**
	 * Teste l'�galit� sur l'identifiant du troncon
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Troncon other = (Troncon) obj;
		if (idTroncon == null) {
			if (other.idTroncon != null)
				return false;
		} else if (!idTroncon.equals(other.idTroncon))
			return false;
		return true;
	}

	public String toString(){
		return "Troncon :"+ idTroncon+" entre " +intersectionPrecedenteSensHoraire.toString()+" et "+intersectionSuivanteSensHoraire.toString(); 
	}

	public double[] getSpeedDistribution() {
		double[] res  = new double [devices.size()];
		int i = 0;
		for (Device d : devices) {
			res[i] = d.getSpeed();
			i++;
		}
		return res;
	}

	public double getMedianSpeed() {
		if (devices.size() > 0) {
			double[] values = getSpeedDistribution();
			Arrays.sort(values);
			double median;
			if(values.length==1){
				median = values[0];
			}
			else{
				if (values.length % 2 == 0)
					median = ((double)values[values.length/2] + (double)values[values.length/2 - 1])/2;
				else
					median = (double) values[values.length/2];

			}
			return median;

		} else {
			System.out
			.println("RoadSection.getAverageSpeed() : no devices on roadsection");
			return vitessemax;
		}
	}

	public double getAverageSpeed(DeviceInfo di) {
		if (devices.size() > 0) {
			double[] values = getSpeedDistribution();
			System.out.println("VALUES = "+values.toString());
			double sum = 0.0;
			for (int i = 0; i < devices.size(); i++) {
				sum += values[i];
			}
			String indicatif;
			if(sum/devices.size()>70){
				indicatif = "fluide";}
			else{
				if(sum/devices.size()<50){
					indicatif = "fort ralentissement";
				}
				else{
					indicatif = "dense";
				}
			}
			tempsEtatConstant(indicatif, di);
			
			return sum/devices.size();
		} else {
			System.out
			.println("RoadSection.getAverageSpeed() : no devices on roadsection");
			tempsEtatConstant(this.indicatif,di);
			return vitessemax;
		}
	}

	// fonction qui calcule toutes les vitesses et retourne qqchose
	public void maj(DeviceInfo di) {
		System.out.println("on est dans maj2");
		//this.vitesseMediane = getMedianSpeed();
		this.vitesseMoyenne = getAverageSpeed(di);
	}

	public double getVitesseMoyenne() {
		return vitesseMoyenne;
	}

	public void setVitesseMoyenne(double vitesseMoyenne) {
		this.vitesseMoyenne = vitesseMoyenne;
	}

	public double getVitesseMediane() {
		return vitesseMediane;
	}

	public void setVitesseMediane(double vitesseMediane) {
		this.vitesseMediane = vitesseMediane;
	}
	public double  tempsAParcourir(){
		return distance/vitesseMoyenne;
	}
	public void tempsEtatConstant(String indicatif, DeviceInfo di){
		//TODO trouver un moyen d'acceder à la timestamp
//		System.out.println("on est dans tempsEtatConstant()");
//		System.out.println("this.indicatif = "+this.indicatif);
//		System.out.println("inficatif" + indicatif);
		double resultat;
		if(!indicatif.equals(this.indicatif)){
//			System.out.println("on a changé d'état");
			resultat = 0;
			this.tempsDernierChangement = di.getTempsReleve();
			this.indicatif = indicatif;
			//TODO : changer la valeur du dernier changeemnt pour la mettre à la timestamp du device impliqué. 
		}
		else{
			resultat = di.getTempsReleve()-this.tempsDernierChangement;
			// System.out.println("on est resté dans le même état");
		}
		this.tempsEtatConstant = resultat;
	}

	public String getIndicatif() {
		return indicatif;
	}

	public void setIndicatif(String indicatif) {
		this.indicatif = indicatif;
	}

	public double getTempsEtatConstant() {
		return tempsEtatConstant;
	}

	public void setTempsEtatConstant(double tempsEtatConstant) {
		this.tempsEtatConstant = tempsEtatConstant;
	}	
	
	
}
