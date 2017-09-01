/**
 * 
 */
package com.ensai.pfe.wasabe.server.route;

import com.ensai.pfe.wasabe.server.metier.Device;
import com.ensai.pfe.wasabe.server.metier.DeviceInfo;

/**
 * @author ensai
 *
 */
public interface IRoute {

	/**
	 * 
	 * Cette fonction d�terminera l'itineraire � suivre pour un v�hicule qui se
	 * trouve quelque part sur le troncon identifi� par idTroncon, qui circule
	 * dans le sens d�termin� par sensHoraire (ie, si sensHoraire = true, il va
	 * dans le sens des aiguilles d'une montre) pour aller vers une destination
	 * identifi�e par son nom tel qu'il est retourn� dans un DeviceInfo (ie, un
	 * nombre entre 1 et 17 inclus
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param nomDestination
	 * @param idTroncon
	 * @param sensHoraire
	 * @return
	 */
	Itineraire getItineraire(String nomDestination, String idTroncon,
			boolean sensHoraire);
	
	
	/**
	 * 
	 * Cette fonction s'occupera de l'ajout d'un deviceInfo dans la partie m�tier (POJO) de la route (ici, l'anneau routier), ce qui inclut potentiellement :
	 * - l'ajout � un troncon
	 * - la suppression du troncon pr�cedent, s'il �tait deja pr�sent sur un troncon pr�cendent.
	 * 
	 * 
	 * 
	 * @param deviceInfo
	 */

	void rajouterDevice(Device d, String idTroncon, DeviceInfo di);
	

}
