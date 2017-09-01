/**
 * 
 */
package com.ensai.pfe.wasabe.server.route;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.ensai.pfe.wasabe.server.metier.Coord;
import com.ensai.pfe.wasabe.server.metier.Device;

/**
 * @author ensai
 *
 */
public class TronconTest {

	Troncon t1;
	Troncon t2;
	Troncon t3;
	final String IDENT_I1 = "aaaaaaffff";
	final String IDENT_I2 = "aaaaaaeeee";
	final String IDENT_I3 = "aaaaaadddd";
	final String IDENT_I4 = "aaaaaacccc";
	Intersection i1 = new Intersection(IDENT_I1, "1", new Coord(1, 2));
	Intersection i2 = new Intersection(IDENT_I2, "2", new Coord(1, 2));
	Intersection i3 = new Intersection(IDENT_I3, "2", new Coord(1, 2));
	Intersection i4 = new Intersection(IDENT_I4, "2", new Coord(1, 2));

	@Before
	public void setUp() {
		t1 = new Troncon("AAAAAAAFFFFFF", 90, 500, new ArrayList<Device>(), 90);
		t2 = new Troncon("AAAAAAAEEEEEE", 45, 1400, new ArrayList<Device>(),
				160);
		t3 = new Troncon("AAAAAAADDDDDD", 0, 300, new ArrayList<Device>(), 45);
		t1.setIntersectionPrecedenteSensHoraire(i1);
		t1.setintersectionSuivanteSensHoraire(i2);
		t2.setIntersectionPrecedenteSensHoraire(i2);
		t2.setintersectionSuivanteSensHoraire(i3);
		t3.setIntersectionPrecedenteSensHoraire(i3);
		t3.setintersectionSuivanteSensHoraire(i4);
	}

	@Test
	public void testPrecedentSuivant() {
		assertTrue(t1.getIntersectionPrecedenteSensHoraire().equals(
				new Intersection(IDENT_I1, "peuimportevoirtestintersection",
						new Coord(1, 2))));
		assertTrue(t3.getIntersectionSuivanteSensHoraire().equals(
				new Intersection(IDENT_I4, "peuimportevoirtestintersection",
						new Coord(1, 2))));

	}

}
