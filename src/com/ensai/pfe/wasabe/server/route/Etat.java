package com.ensai.pfe.wasabe.server.route;

public class Etat {
private String indicatif;
private double tempsEtatConstant;

public Etat(String indicatif) {
	super();
	this.indicatif = indicatif;
}

public Etat(String indicatif, double tempsEtatConstant) {
	super();
	this.indicatif = indicatif;
	this.tempsEtatConstant = tempsEtatConstant;
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
