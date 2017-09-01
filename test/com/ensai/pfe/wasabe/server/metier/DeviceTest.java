/**
 * 
 */
package com.ensai.pfe.wasabe.server.metier;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ensai
 *
 */
public class DeviceTest {

	static Device dev;
	public final static String IDDEVICE="qqq";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// DeviceInfo d1 = new DeviceInfo(5, latitude, longitude, precision, id,
		// destination)
		DeviceInfo d6 = new DeviceInfo(20.0, 48.0477, -1.73994, 2.0, "aaaaa",
				"5 Gymnases");
		DeviceInfo d5 = new DeviceInfo(30.0, 48.0478, -1.73993, 2.0, "aaaaa",
				"4b");
		DeviceInfo d4 = new DeviceInfo(40.0, 48.0481, -1.74081, 2.0, "aaaaa",
				"4 Etangs");
		DeviceInfo d3 = new DeviceInfo(50.0, 48.0478, -1.74191, 2.0, "aaaaa",
				"3 Institut de Gestion de Rennes");




		ArrayList<DeviceInfo> devinfos = new ArrayList<DeviceInfo>();
		devinfos.add(d6);
		devinfos.add(d5);
		devinfos.add(d4);
		devinfos.add(d3);
		dev = new Device(IDDEVICE, devinfos, true);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {


		System.out.println("Estimated device speed, 2 measures : "
				+ dev.getSpeed(2));
		System.out.println("Estimated device speed, 3 measures: "
				+ dev.getSpeed(3));
		System.out.println("Estimated device speed, 4 measures : "
				+ dev.getSpeed(4));
		
		System.out.println("Estimated device bearing : "+dev.getBearing() );

	}

}
