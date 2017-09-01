/**
 * 
 */
package com.ensai.pfe.wasabe.server.route;

import com.ensai.pfe.wasabe.server.metier.Coord;

/**
 * @author ensai
 *
 */
public class Intersection {

	
	// Attributs
	private String idIntersection;
	private String nom;
	private Coord coord;

	
	// Constructeurs
	/**
	 * @param idIntersection
	 *            identifiant en base
	 * @param nom
	 *            nom de l'intersection, avec lequel on doit pouvoir identifier
	 *            à partir de la destination renvoyée l'intersection de
	 *            destination
	 * @param coord la coordonnée (type maison) de l'intersect
	 */
	public Intersection(String idIntersection, String nom, Coord coord) {
		super();
		this.idIntersection = idIntersection;
		this.nom = nom;
		this.coord = coord;
	}

	
	// Getters, Setters
	public String getIdIntersection() {
		return idIntersection;
	}

	public void setIdIntersection(String idIntersection) {
		this.idIntersection = idIntersection;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	// Methodes
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idIntersection == null) ? 0 : idIntersection.hashCode());
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
		Intersection other = (Intersection) obj;
		if (idIntersection == null) {
			if (other.idIntersection != null)
				return false;
		} else if (!idIntersection.equals(other.idIntersection))
			return false;
		return true;
	}


	/**
	 * @return the coord
	 */
	public Coord getCoord() {
		return coord;
	}


	/**
	 * @param coord the coord to set
	 */
	public void setCoord(Coord coord) {
		this.coord = coord;
	}



	public String toString(){
		return nom + coord.toString();
	}

}
