package com.ensai.pfe.wasabe.server.route;

public class TronconEnvoye {
private String nomAffiche;
private double vitesseMoyenne;
private double tempsParcours;
private Etat etat;


public TronconEnvoye() {
	super();
}



public TronconEnvoye(String nomAffiche, double vitesseMoyenne,
		double tempsParcours, Etat etat) {
	super();
	this.nomAffiche = nomAffiche;
	this.vitesseMoyenne = vitesseMoyenne;
	this.tempsParcours = tempsParcours;
	this.etat = etat;
}



public String getNomAffiche() {
	return nomAffiche;
}

public void setNomAffiche(String nomAffiche) {
	this.nomAffiche = nomAffiche;
}

public double getVitesseMoyenne() {
	return vitesseMoyenne;
}

public void setVitesseMoyenne(double vitesseMoyenne) {
	this.vitesseMoyenne = vitesseMoyenne;
}

public Etat getEtat() {
	return etat;
}

public void setEtat(Etat etat) {
	this.etat = etat;
}



public double getTempsParcours() {
	return tempsParcours;
}

public void setTempsParcours(double tempsParcours) {
	this.tempsParcours = tempsParcours;
}

}
