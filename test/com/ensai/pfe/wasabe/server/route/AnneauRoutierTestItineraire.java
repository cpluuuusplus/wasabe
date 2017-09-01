/**
 * 
 */
package com.ensai.pfe.wasabe.server.route;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.ensai.pfe.wasabe.server.metier.Coord;
import com.ensai.pfe.wasabe.server.metier.Device;

/**
 * @author ensai
 *
 */
public class AnneauRoutierTestItineraire {

	final String IDENT_I1 = "aaaaaaffff";
	final String IDENT_I2 = "aaaaaaeeee";
	final String IDENT_I3 = "aaaaaadddd";
	final String IDENT_I4 = "aaaaaacccc";
	final String IDENT_T1 = "AAAAAAAFFFFFF";
	final String IDENT_T2 = "AAAAAAAEEEEEE";
	final String IDENT_T3 = "AAAAAAADDDDDD";
	final String IDENT_T4 = "AAAAAAACCCCCC";
	AnneauRoutier ar;

	@Before
	public void setUp() {

		ar = new AnneauRoutier();
		Troncon t1;
		Troncon t2;
		Troncon t3;
		Troncon t4;

		Intersection i1 = new Intersection(IDENT_I1, "1", new Coord(0, 0));
		Intersection i2 = new Intersection(IDENT_I2, "2", new Coord(0, 1));
		Intersection i3 = new Intersection(IDENT_I3, "3", new Coord(1, 1));
		Intersection i4 = new Intersection(IDENT_I4, "4", new Coord(1, 0));

		t1 = new Troncon(IDENT_T1, 90, 500, new ArrayList<Device>(), 90);
		t2 = new Troncon(IDENT_T2, 360, 500, new ArrayList<Device>(), 160);
		t3 = new Troncon(IDENT_T3, 180, 500, new ArrayList<Device>(), 45);
		t4 = new Troncon(IDENT_T4, 240, 500, new ArrayList<Device>(), 75);

		t1.setIntersectionPrecedenteSensHoraire(i1);
		t1.setintersectionSuivanteSensHoraire(i2);
		t2.setIntersectionPrecedenteSensHoraire(i2);
		t2.setintersectionSuivanteSensHoraire(i3);
		t3.setIntersectionPrecedenteSensHoraire(i3);
		t3.setintersectionSuivanteSensHoraire(i4);
		t4.setIntersectionPrecedenteSensHoraire(i4);
		t4.setintersectionSuivanteSensHoraire(i1);

		ar.addIntersection(i1);
		ar.addIntersection(i2);
		ar.addIntersection(i3);
		ar.addIntersection(i4);

		ar.addTroncons(t1);
		ar.addTroncons(t2);
		ar.addTroncons(t3);
		ar.addTroncons(t4);

	}

	@Test
	public void test() {

		assertTrue(ar.getLAutreIntersectionDuTronconIDbyID(IDENT_I2, IDENT_T1)
				.equals(IDENT_I1));

		System.out.println(ar.getItineraire("1", IDENT_T2, true));
		System.out.println(" -------- ");
		System.out.println(ar.getItineraire("1", IDENT_T2, false));

	}

}
