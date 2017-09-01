/**
 * 
 */
package com.ensai.pfe.wasabe.server.route;

import java.util.ArrayList;
import java.util.List;

import com.ensai.pfe.wasabe.server.metier.DeviceInfo;

/**
 * @author ensai
 *
 */
public class Itineraire {


	// Attributs
	private Troncon duDevice;
	private List<Troncon> aParcourir = new ArrayList<Troncon>();

	/**
	 * @param tronconActuelDuDevice
	 */
	public Itineraire(Troncon tronconActuelDuDevice) {
		duDevice = tronconActuelDuDevice;
	}

	/**
	 * 
	 */
	public Itineraire() {
	}

	public void addTroncon(Troncon t){
		aParcourir.add(t);
	}



	public Troncon getDuDevice() {
		return duDevice;
	}

	public void setDuDevice(Troncon duDevice) {
		this.duDevice = duDevice;
	}

	public List<Troncon> getaParcourir() {
		return aParcourir;
	}

	public void setaParcourir(List<Troncon> aParcourir) {
		this.aParcourir = aParcourir;
	}

	public String toString(){
		String res = "Itineraire partant de "+duDevice.toString();
		if(!aParcourir.isEmpty()){
			for(Troncon t:aParcourir){
				res += "\n " + t.toString();
			}
		}
		return res;

	}

	public ItineraireEnvoye fromItineraireToItineraireEnvoye(boolean isSensHoraire, DeviceInfo di){
		//System.out.println("on est dans fromItineraireEnvoye");
		ItineraireEnvoye it = new ItineraireEnvoye();
		//System.out.println("nb devices " +duDevice.getDevices().size() );
		List<TronconEnvoye> tronconsEnvoye = new ArrayList<TronconEnvoye>();

		//on va calculer le temps total de trajet
		double tempsTotal = 0;
		//on calcul le temps qu'il faut pour parcourir le troncon sur lequel se trouve le device
		tempsTotal = duDevice.tempsAParcourir();
		TronconEnvoye envoyeDuDevice = new TronconEnvoye();
		String nomAffiche = "";
		if(isSensHoraire){
			nomAffiche = duDevice.getIntersectionSuivanteSensHoraire().getNom();
		}else{
			nomAffiche = duDevice.getIntersectionPrecedenteSensHoraire().getNom();
		}
		envoyeDuDevice.setNomAffiche(nomAffiche);

		envoyeDuDevice.setTempsParcours(duDevice.tempsAParcourir());

		//TODO rajouter l'etat du bordel
		double vitesse = duDevice.getAverageSpeed(di);
		envoyeDuDevice.setVitesseMoyenne(vitesse);
		String indicatif;
		if(vitesse>70){
			indicatif = "fluide";
		}else{
			if(vitesse<50){
				indicatif = "fort ralentissement";
			}
			else{
				indicatif = "dense";
			}
		}
		
		double tempsEtatConstant = duDevice.getTempsEtatConstant();
		envoyeDuDevice.setEtat(new Etat(indicatif, tempsEtatConstant));
		tronconsEnvoye.add(envoyeDuDevice);

		//on parcours l'ensemble des troncons à parcourir
		for(Troncon t:aParcourir){
			//on ajoute le temps de parcours
			tempsTotal = tempsTotal + t.tempsAParcourir();
			envoyeDuDevice = new TronconEnvoye();
			nomAffiche = "";

			if(isSensHoraire){
				nomAffiche = t.getIntersectionSuivanteSensHoraire().getNom();
			}else{
				nomAffiche = t.getIntersectionPrecedenteSensHoraire().getNom();
			}
			envoyeDuDevice.setNomAffiche(nomAffiche);
			envoyeDuDevice.setVitesseMoyenne(vitesse);
			envoyeDuDevice.setTempsParcours(t.tempsAParcourir());


			//TODO rajouter l'etat du bordel
			vitesse = t.getAverageSpeed(di);
			if(vitesse>70){
				indicatif = "fluide";
			}else{
				if(vitesse<50){
					indicatif = "fort ralentissement";
				}
				else{
					indicatif = "dense";
				}
			}
			tempsEtatConstant = t.getTempsEtatConstant();
			envoyeDuDevice.setEtat(new Etat(indicatif, tempsEtatConstant));
			tronconsEnvoye.add(envoyeDuDevice);
		}
		// System.out.println("temps total = "+tempsTotal);
		it.setTempsTotal(tempsTotal);
		it.setTroncons(tronconsEnvoye);
		return it;

	}

}
