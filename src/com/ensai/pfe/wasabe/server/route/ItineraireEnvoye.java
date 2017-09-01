package com.ensai.pfe.wasabe.server.route;

import java.util.ArrayList;
import java.util.List;

public class ItineraireEnvoye {

	private double tempsTotal;
	private List<TronconEnvoye> troncons = new ArrayList<TronconEnvoye>();
	
	
	
	public ItineraireEnvoye() {
		super();
	}
	
	public ItineraireEnvoye(double tempsTotal, List<TronconEnvoye> troncons) {
		super();
		this.tempsTotal = tempsTotal;
		this.troncons = troncons;
		System.out.println("on a crée notre intineraire à envoyer");
	}
	
	
	public double getTempsTotal() {
		return tempsTotal;
	}
	public void setTempsTotal(double tempsTotal) {
		this.tempsTotal = tempsTotal;
	}
	public List<TronconEnvoye> getTroncons() {
		return troncons;
	}
	public void setTroncons(List<TronconEnvoye> troncons) {
		this.troncons = troncons;
	}
		
}
