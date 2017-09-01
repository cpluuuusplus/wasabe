

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ensai.pfe.wasabe.server.dao.DAODeviceInfo;

public class DAOJunit {

	@Test
	public void test() {
		DAODeviceInfo dao = new DAODeviceInfo();
		System.out.println(dao.saveDevice());		
		assertTrue(true);
	}

}
