package com.ensai.pfe.wasabe.server.webservice;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.Test;

import com.ensai.pfe.wasabe.server.dao.DAOReseauRoutier;
import com.ensai.pfe.wasabe.server.metier.Device;
import com.ensai.pfe.wasabe.server.metier.DeviceInfo;
import com.ensai.pfe.wasabe.server.route.AnneauRoutier;
import com.ensai.pfe.wasabe.server.route.Itineraire;
import com.ensai.pfe.wasabe.server.route.ItineraireEnvoye;

public class WasabeServletTest {

	@Test
	public void test() {
		DAOReseauRoutier dao = new DAOReseauRoutier();
		AnneauRoutier AR = dao.readFromBase();
		WasabeServlet was = new WasabeServlet();
		JSONObject json = new JSONObject();
		
		System.out.println(AR.getTroncons().get(1).getIndicatif());
		System.out.println(AR.getTroncons().toString());

		DeviceInfo d6 = new DeviceInfo(0.5,1.3,1.4,0.0,"123456","5");
		d6.setAngle(0);
		d6.setSpeed(40.0);

		ArrayList<DeviceInfo> devinfos = new ArrayList<DeviceInfo>();
		devinfos.add(d6);

		Device dev = new Device("123456", devinfos, true);
		
		DeviceInfo d7 = new DeviceInfo(1.2,1.3,1.4,0.0,"2345","6");
		d7.setAngle(0);
		d7.setSpeed(40.0);

		ArrayList<DeviceInfo> devinfos2 = new ArrayList<DeviceInfo>();
		devinfos2.add(d7);
		AR.rajouterDevice(dev, "551021c14b8eac45513a2d29",d6);
			
		
		ItineraireEnvoye ite = new ItineraireEnvoye();
		Itineraire itineraire = AR.getItineraire(d6.getDestination(), "551021c14b8eac45513a2d29",false);
		ite = itineraire.fromItineraireToItineraireEnvoye(true, (dev.getDeviceinfos().get(dev.getDeviceinfos().size()-1)));
		System.out.println(was.ecriture(json,  ite));
		Device dev2 = new Device("2345", devinfos2, true);
		AR.rajouterDevice(dev2, "550bfd82986c704af3be7976", d7);
		itineraire = AR.getItineraire(d6.getDestination(), "551021c14b8eac45513a2d29",false);
		
		ite = itineraire.fromItineraireToItineraireEnvoye(true, (dev2.getDeviceinfos().get(dev2.getDeviceinfos().size()-1)));
		System.out.println(was.ecriture(json, ite));
	}

}
