/**
 * 
 */
package com.ensai.pfe.wasabe.server.route;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.ensai.pfe.wasabe.server.dao.DAOReseauRoutier;
import com.ensai.pfe.wasabe.server.metier.Device;
import com.ensai.pfe.wasabe.server.metier.DeviceInfo;

/**
 * @author ensai
 *
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
@Startup
public class AnneauRoutier implements IRoute {

	// Attributes
	private List<Intersection> intersections = new ArrayList<Intersection>();
	private List<Troncon> troncons = new ArrayList<Troncon>();

	// Constructeurs

	public AnneauRoutier() {

	}

	/**
	 * 
	 * 
	 * @param intersections
	 *            Liste d'intersectio,s
	 * @param troncons
	 *            Liste de troncons
	 */
	public AnneauRoutier(List<Intersection> intersections,
			List<Troncon> troncons) {
		super();
		this.intersections = intersections;
		this.troncons = troncons;
	}

	// Getters, Setters
	@Lock(LockType.READ)
	public List<Intersection> getIntersections() {
		return intersections;
	}
	@Lock(LockType.WRITE)
	public void setIntersections(List<Intersection> intersections) {
		this.intersections = intersections;
	}
	@Lock(LockType.WRITE)
	public void addIntersection(Intersection intersection) {
		intersections.add(intersection);
	}
	@Lock(LockType.READ)
	public List<Troncon> getTroncons() {
		return troncons;
	}
	@Lock(LockType.WRITE)
	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}
	@Lock(LockType.WRITE)
	public void addTroncons(Troncon troncon) {
		troncons.add(troncon);
	}

	// Methodes

	/**
	 * Cette fonction r�cupere un troncon parmi ceux pr�sents dans AnneauRoutier
	 * par son identifiant.
	 * 
	 * Si celui ci n'est pas trouv�, affiche une erreur sur la console et
	 * renvoie null;
	 * 
	 * @param identifiantTroncon
	 *            l'identifiant du troncon � trouver (String)
	 * @return le troncon si celui ci est trouv�, sinon null (avec une erreur
	 *         sur la console)
	 */
	@Lock(LockType.READ)
	public Troncon getTronconParIdentifiant(String identifiantTroncon) {
		for (Troncon t : troncons) {
			if (t.getIdTroncon().equals(identifiantTroncon)) {
				return t;
			}
		}
//		System.err
//		.println("AR.getTronconParIdentifiant : aucun troncon actuellement en POJO ne porte l'identifiant "
//				+ identifiantTroncon
//				+ " : null renvoy� et nullPointerExceptioon en approche");
		return null;
	}

	/**
	 * 
	 * Cette fonction r�cup�re l'intersection qui porte pour nom le premier
	 * parametre de la fonction
	 * 
	 * Si celui ci n'est pas trouv�, affiche une erreur sur la console et
	 * renvoie null;
	 * 
	 * @param nomIntersection
	 * @return
	 */
	@Lock(LockType.READ)
	public Intersection getIntersectionParNom(String nomIntersection) {
		for (Intersection inter : intersections) {
			if (inter.getNom().equals(nomIntersection)) {
				return inter;
			}
		}
//		System.err
//		.println("AR.getIntersectionParNom : aucune intersection actuellement en POJO ne porte le nom "
//				+ nomIntersection
//				+ " : null renvoy� et nullPointerException en approche");
		return null;
	}

	/**
	 * 
	 * Cette fonction r�cup�re l'intersection qui porte pour identifiant le
	 * premier parametre de la fonction
	 * 
	 * Si celui ci n'est pas trouv�, affiche une erreur sur la console et
	 * renvoie null;
	 * 
	 * @param identifiant
	 * @return
	 */
	@Lock(LockType.READ)
	public Intersection getIntersectionParIdentifiant(String identifiant) {
		for (Intersection inter : intersections) {
			if (inter.getIdIntersection().equals(identifiant)) {
				return inter;
			}
		}
//		System.err
//		.println("AR.getIntersectionParNom : aucune intersection actuellement en POJO ne porte l'identifiant "
//				+ identifiant
//				+ " : null renvoy� et nullPointerException en approche");
		return null;
	}

	/**
	 * 
	 * Fonction analogue de getLAutreTronconDeLIntersection, travaille sur les
	 * identifiants plutot que sur les objets
	 * 
	 * @param idPremierTroncon
	 * @param idInter
	 * @return id Autre troncon de l'intersection
	 */
	@Lock(LockType.READ)
	public String getLAutreTronconDeLIntersectionIDbyID(
			String idPremierTroncon, String idInter) {
		return getLAutreTronconDeLIntersection(
				getTronconParIdentifiant(idPremierTroncon),
				getIntersectionParIdentifiant(idInter)).getIdTroncon();
	}

	/**
	 * 
	 * Cette fonction, a partir d'une intersection, retourne le troncon oppos� �
	 * celui pass� en parametre. * SH :sens horaire SAH : sens anti horaire
	 * 
	 * Si vous comprenez pas, faites un dessin et une disjonction des cas Si
	 * vous comprenez toujours pas et qu'il n'y a pas de test associ� � cette
	 * classe il est possible aue j'aie fait nimp(ortequoi)
	 * 
	 * @param lePremierTroncon
	 * @param inter
	 * @return
	 */
	@Lock(LockType.READ)
	public Troncon getLAutreTronconDeLIntersection(Troncon lePremierTroncon,
			Intersection inter) {

		// dans SH si le troncon est situ� apr�s l'intersection
		if (inter.equals(lePremierTroncon
				.getIntersectionPrecedenteSensHoraire())) {
			for (Troncon t : troncons) {
				// On veut recupere le troncon qui a pour prochaine intersection
				// celle pass�e en param
				if (inter.equals(t.getIntersectionSuivanteSensHoraire())) {
					return t;
				}
			}
		}

		// Dans SH si le troncon est situ� avant l'intersection
		if (inter.equals(lePremierTroncon.getIntersectionSuivanteSensHoraire())) {
			for (Troncon t : troncons) {
				// On veut recuperer le troncon qui a pour intersection
				// pr�cedente celle pass�e en param
				if (inter.equals(t.getIntersectionPrecedenteSensHoraire())) {
					return t;
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * Fonction analogue de getLAutreIntersectionDuTroncon, travaille sur les
	 * identifiants plutot que sur les objets
	 * 
	 * @param idInter
	 * @param idTroncon
	 * @return id intersection du troncon
	 */
	@Lock(LockType.READ)
	public String getLAutreIntersectionDuTronconIDbyID(String idInter,
			String idTroncon) {
		return getLAutreIntersectionDuTroncon(
				getIntersectionParIdentifiant(idInter),
				getTronconParIdentifiant(idTroncon)).getIdIntersection();
	}

	/**
	 * 
	 * 
	 * Cette fonction, � partir d'un troncon, retourne l'intersection oppos�e �
	 * celle pass�e en parametre
	 * 
	 * @param laPremiereIntersection
	 * @param t
	 * @return
	 */
	@Lock(LockType.READ)
	public Intersection getLAutreIntersectionDuTroncon(
			Intersection laPremiereIntersection, Troncon t) {
		if (t.getIntersectionPrecedenteSensHoraire().equals(
				laPremiereIntersection)) {
			return t.getIntersectionSuivanteSensHoraire();
		} else {
			return t.getIntersectionPrecedenteSensHoraire();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ensai.pfe.wasabe.server.route.IRoute#getItineraire(java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	@Lock(LockType.READ)
	public Itineraire getItineraire(String nomDestination, String idTroncon,
			boolean sensHoraire) {

		// identifie le nom de destination � une intersection
		Intersection destination = getIntersectionParNom(nomDestination);
		// identifie l'id troncon � un troncon
		Troncon tronconActuelDuDevice = getTronconParIdentifiant(idTroncon);

		Itineraire res = new Itineraire();
		res.setDuDevice(tronconActuelDuDevice);

		// Le troncon courant est test� pour savoir si il a pour prochaine
		// intersection la destination
		Troncon tronconCourant = tronconActuelDuDevice;

		if (sensHoraire) {
			// le troncon qu'on recherche est apr�s
			// Pendant qu la destination n'est pas l'autre intersection du
			// troncon courant
			while (!destination.equals(getLAutreIntersectionDuTroncon(
					tronconCourant.getIntersectionSuivanteSensHoraire(),
					tronconCourant))) {
				// si le troncon actuel n'est par celui du device
				if (!tronconCourant.equals(tronconActuelDuDevice)) {
					// On ajoute le nouveau troncon courant a l'itineraire
					// la condition est pour �viter la double inclusion du
					// troncon du device dans la liste
					res.addTroncon(tronconCourant);

				}

				// On r�actualise le troncon courant
				tronconCourant = getLAutreTronconDeLIntersection(
						tronconCourant,
						tronconCourant.getIntersectionSuivanteSensHoraire());

			}

		} else {
			// idem en remplacant suivant par precedent comme on travaille dans
			// l'autre sens
			while (!destination.equals(getLAutreIntersectionDuTroncon(
					tronconCourant.getIntersectionPrecedenteSensHoraire(),
					tronconCourant))) {
				// si le troncon actuel n'est par celui du device
				if (!tronconCourant.equals(tronconActuelDuDevice)) {
					// On ajoute le nouveau troncon courant a l'itineraire
					// la condition est pour �viter la double inclusion du
					// troncon du device dans la liste
					res.addTroncon(tronconCourant);

				}

				// On r�actualise le troncon courant
				tronconCourant = getLAutreTronconDeLIntersection(
						tronconCourant,
						tronconCourant.getIntersectionPrecedenteSensHoraire());

			}
		}

		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ensai.pfe.wasabe.server.route.IRoute#rajouterDeviceInfo(com.ensai
	 * .pfe.wasabe.server.metier.DeviceInfo)
	 */
	@Override

	@Lock(LockType.WRITE)
	public void rajouterDevice(Device d, String idTroncon, DeviceInfo di) {
//		System.out.println(di.getSpeed());
//		System.out.println("on est dans rajouterDevice()");
//		System.out.println(d.getDeviceinfos().toString());
		// on vérifie si le device appartient déjà à un troncon
		String idAncienTroncon = idTroncon;
		boolean trouve = false;
		boolean aChange = false;
		// on trouve sur quel troncon se trouvait le device avant
		for(Troncon t:troncons){
			t.maj(di);
			if(!trouve){
				for(int i=0; i<t.getDevices().size();i++){
					if(t.getDevices().get(i).getIdDevice().equals(d.getIdDevice())){
						idAncienTroncon = t.getIdTroncon();
						trouve = true;
						// si le device a change de troncon alors on l'enleve de l'ancien troncon
						if(!(idAncienTroncon.equals(idTroncon))){
							List<Device> desDevice = new ArrayList<Device>();
							desDevice = t.getDevices();
							desDevice.remove(i);
							t.setDevices(desDevice);
							aChange = true;
							// System.out.println("ICIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
							t.maj(di);
							//maj(t.getIdTroncon(), di);
//TODO  :  supprimer le fonction maj(t.getId) qui ne sert à rien
						}
					}
				}
			}
		}
		if(aChange==true || trouve==false){  //si le device a change de troncon
			//ou qu'il n'avait pas de troncon alors on l'ajoute
			for(Troncon t:troncons){
				if(t.getIdTroncon().equals(idTroncon)){
					// System.out.println("on a trouvé le troncon");
					List<Device> desDevice = new ArrayList<Device>();
					desDevice = t.getDevices();
					desDevice.add(d);
					
					t.setDevices(desDevice);
					t.maj(di);
					// System.out.println("COUCOU JE SUIS LA");
				}
			}
		}
	}

	@Lock(LockType.READ)
	public ArrayList<Device> getAllDevices(){
		ArrayList<Device> res = new ArrayList<Device>();
		for(Troncon t: troncons){
			res.addAll(t.getDevices());
		}
		return res;
	}

	@PostConstruct
	public void initialiser(){
		DAOReseauRoutier dao = new DAOReseauRoutier();
		AnneauRoutier truc = dao.readFromBase();
		this.intersections=truc.getIntersections();
		this.troncons=truc.getTroncons();
	}
	
	public void maj(String idTroncon, DeviceInfo di){
		// System.out.println("on est dans maj1()");
		getTronconParIdentifiant(idTroncon).maj(di);
		
	}
}
