/**
 * 
 */
package com.ensai.pfe.wasabe.server.metier;

/**
 * @author ensai
 *
 */

public class Point {
	protected String identifiant;
	protected Coord coord;
	private String idTroncon;

	public Point(String identifiant, Coord coord, String idTroncon) {
		super();
		this.identifiant = identifiant;
		this.coord = coord;
		this.idTroncon = idTroncon;

		// System.out.println("on a crée un point");
	}

	public Point() {
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}


	public String getIdTroncon() {
		return idTroncon;
	}

	public void setIdTroncon(String idTroncon) {
		this.idTroncon = idTroncon;
	}

	/**
	 * @return the coord
	 */
	public Coord getCoord() {
		return coord;
	}

	/**
	 * @param coord
	 *            the coord to set
	 */
	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coord == null) ? 0 : coord.hashCode());
		result = prime * result
				+ ((idTroncon == null) ? 0 : idTroncon.hashCode());
		result = prime * result
				+ ((identifiant == null) ? 0 : identifiant.hashCode());
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
		Point other = (Point) obj;
		if (coord == null) {
			if (other.coord != null)
				return false;
		} else if (!coord.equals(other.coord))
			return false;
		return true;
	}

}