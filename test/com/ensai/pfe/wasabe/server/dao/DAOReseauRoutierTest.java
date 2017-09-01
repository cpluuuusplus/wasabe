/**
 * 
 */
package com.ensai.pfe.wasabe.server.dao;

import org.junit.Test;

import com.ensai.pfe.wasabe.server.route.AnneauRoutier;

/**
 * @author ensai
 *
 */
public class DAOReseauRoutierTest {

	@Test
	public void test() {
		DAOReseauRoutier dao = new DAOReseauRoutier();
		AnneauRoutier anneauRoutier = new AnneauRoutier();
		anneauRoutier = dao.readFromBase();
		System.out.println(anneauRoutier.getIntersections().toString());
		System.out.println(anneauRoutier.getTroncons().toString());
		
		// depart 12-13, arrivée 5, SH
		System.out.println(anneauRoutier.getItineraire("5", "55080d0c0b8c3c7917ae314b", true));

		// depart 12-13, arrivée 5, SAH
		System.out.println(anneauRoutier.getItineraire("5", "55080d0c0b8c3c7917ae314b", false));

		
	}

}
