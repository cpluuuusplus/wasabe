package com.ensai.pfe.wasabe.server.route;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ensai.pfe.wasabe.server.dao.DAOReseauRoutier;
import com.ensai.pfe.wasabe.server.metier.Device;
import com.ensai.pfe.wasabe.server.metier.DeviceInfo;

public class AnneauRoutierTest {


	AnneauRoutier anneauRoutier;
	@Before
	public void setUp() {
		anneauRoutier = new AnneauRoutier();
		DAOReseauRoutier dao = new DAOReseauRoutier();
		anneauRoutier = dao.readFromBase();

		System.out.println(anneauRoutier.getTroncons().toString());

		DeviceInfo d6 = new DeviceInfo(1.2,1.3,1.4,0.0,"123456","qqpart");
		d6.setAngle(0);
		d6.setSpeed(50.0);

		ArrayList<DeviceInfo> devinfos = new ArrayList<DeviceInfo>();
		devinfos.add(d6);

		Device dev = new Device("123456", devinfos, true);
		List<Device> listDevice = new ArrayList<Device>();
		listDevice = anneauRoutier.getTroncons().get(0).getDevices();
		listDevice.add(dev);
		anneauRoutier.getTroncons().get(0).setDevices(listDevice);	
		System.out.println(anneauRoutier.getTroncons().get(0).getDevices().toString());
		}

	@Test
	public void test() {
		
		DeviceInfo d6 = new DeviceInfo(1.2,1.3,1.4,0.0,"123456","qqpart");
		d6.setAngle(0);
		d6.setSpeed(90.0);

		ArrayList<DeviceInfo> devinfos = new ArrayList<DeviceInfo>();
		devinfos.add(d6);

		Device dev = new Device("123456", devinfos, true);
		anneauRoutier.rajouterDevice(dev, "550947ab44ae6f000b6a6bb9",d6);
		
		System.out.println(anneauRoutier.getTroncons().get(0).getDevices().toString());
		System.out.println(anneauRoutier.getTroncons().get(1).getDevices().toString());
	}
}
