package com.ensai.pfe.wasabe.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ensai.pfe.wasabe.server.route.AnneauRoutier;

public class testVitesseMax {

	@Test
	public void test() {
		DAOReseauRoutier dao = new DAOReseauRoutier();
		AnneauRoutier anneauRoutier = new AnneauRoutier();
		anneauRoutier = dao.readFromBase();
		System.out.println("vitesseMax" + anneauRoutier.getTroncons().get(1).getVitesseMoyenne());
		fail("Not yet implemented");
	}

}
