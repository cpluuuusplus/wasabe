/**
 * 
 */
package com.ensai.pfe.wasabe.server.route;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ensai.pfe.wasabe.server.metier.Coord;

/**
 * @author ensai
 *
 */
public class IntersectionTest {

	Intersection i1;
	Intersection i2;
	Intersection i3;

	@Before
	public void setUp() {
		i1 = new Intersection("aaaaaaffff", "1", new Coord(1,3));
		i2 = new Intersection("aaaaaaffff", "2", new Coord(5,3));
		i3 = new Intersection("aaaaaaeeee", "2", new Coord(8,3));

	}

	@Test
	public void testEquals() {

		// Since equals() is based solely on id, the following should hold :
		assertTrue(i1.equals(i2));
		assertTrue(!i1.equals(i3));

	}

}
