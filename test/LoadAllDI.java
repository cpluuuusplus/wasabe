

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ensai.pfe.wasabe.server.dao.DAODeviceInfo;

public class LoadAllDI {

	@Test
	public void test() {
		DAODeviceInfo dao = new DAODeviceInfo();
		System.out.println(dao.loadAllDI());
		assertTrue(true);
	}

}
