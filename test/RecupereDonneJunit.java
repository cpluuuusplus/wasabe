

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ensai.pfe.wasabe.server.dao.DAODeviceInfo;
import com.ensai.pfe.wasabe.server.metier.DeviceInfo;

public class RecupereDonneJunit {

	@Test
	public void test() {
		DAODeviceInfo dao = new DAODeviceInfo();
		String idDevice= dao.saveDevice();		
		DeviceInfo di = new DeviceInfo(1.0, 2.0, 3.1, 4.0, idDevice, "loin");
		dao.saveDeviceInfo(di);
		DAODeviceInfo.recupereDonnees(idDevice);
		assertTrue(true);
	}

}
