package com.ensai.pfe.wasabe.server.metier;

import java.util.ArrayList;
import java.util.List;

import com.ensai.pfe.wasabe.server.tools.CalculDistanceDirection;

/**
 * Represents a dingle device, assimilable to a car, on the network
 * 
 * @author ensai
 *
 */
public class Device {
	// Attributs
	private List<DeviceInfo> deviceinfos; // La liste de deviceinfos
											// correspondant au device
	private String idDevice;

	private boolean sensHoraire;
	// Le facteur de lissage dans le calcul de vitesse moyenne :
	// combien de relev�s de vitesse va t on prendre ?
	public final static int AVERAGE_SMOOTHING_FACTOR = 5;

	// Constructeurs
	public Device() {
		deviceinfos = new ArrayList<DeviceInfo>();
	}

	public Device(String idDevice, List<DeviceInfo> dis, boolean sensHoraire) {
		this.setIdDevice(idDevice);
		this.deviceinfos = dis;
		this.sensHoraire = sensHoraire;
		// System.out.println("on a crée notre device");
	}


	
	// M�thodes
	
//	/**
//	 * Doit �tre appel� lorsque le deviceinfo est en base
//	 * 
//	 * 
//	 * @param di
//	 */
//	public void ojpou(DeviceInfo di) {
//		deviceinfos.add(di);
//		int numberDI = deviceinfos.size();
//		if (numberDI > 1) {
//			double speed = getSpeed(Math
//					.min(numberDI, AVERAGE_SMOOTHING_FACTOR));
//			deviceinfos.get(numberDI - 1).setSpeed(speed);
//			// commit la liste en base
//		}
//	}



	public List<DeviceInfo> getDeviceinfos() {
		return deviceinfos;
	}

	public void setDeviceinfos(List<DeviceInfo> deviceinfos) {
		this.deviceinfos = deviceinfos;
	}

	/**
	 * 
	 * Gets the bearing of the device (real angle in degrees between 0 and 360)
	 * based on the two last observations.
	 * 
	 * @return
	 */
	public double getBearing() {

		// TODO get last two deviceInfos from base
		// Get two last DeviceInfos and return the bearing in degrees

		return CalculDistanceDirection.calculateBearing(
				deviceinfos.get(deviceinfos.size() - 2).getLatitude(),
				deviceinfos.get(deviceinfos.size() - 2).getLongitude(),
				deviceinfos.get(deviceinfos.size() - 1).getLatitude(),
				deviceinfos.get(deviceinfos.size() - 1).getLongitude());
	}

	/**
	 * 
	 * Calcule la vitesse d'un device en metres par seconde en se basant sur les
	 * n dernieres observations
	 * 
	 * @param n
	 *            nombre de deviceinfo � utiliser (TODO voir si on aurait pas
	 *            besoin de prendre tous les deviceinfo apr�s un certain temps)
	 * @return
	 */
	public double getSpeed(int n) {

		// TODO get deviceinfos from database and idDevice

		if (!deviceinfos.isEmpty()) {
			// Faire la moyenne des n-1 dernieres diff�rences (entre le dernier
			// et l'avant dernier, l'avant dernier et l'avant-avant-dernier,
			// etc)
			// au cas ou qu'il y aie peu d'enregistrements dans le Device

			int iMin = deviceinfos.size() - Math.min(deviceinfos.size(), n);
			if (iMin < 1) {
				// System.out.println("Device.getSpeed() : pas assez de relev�s");
				iMin = 1;
			}
			int iMax = deviceinfos.size(); // le plus grand des indices

			double deltaTemps = deviceinfos.get(iMax - 1).getTempsReleve()
					- deviceinfos.get(iMin - 1).getTempsReleve();
			double distanceTotale = 0.0;

			for (int i = iMax - 1; i >= iMin; i--) {
				distanceTotale += CalculDistanceDirection.calculateDistance(
						deviceinfos.get(i), deviceinfos.get(i - 1));
			}
			//System.out.println("Device.getSpeed() Calculated speed of "
			//		+ distanceTotale / deltaTemps + " m/s");
			return distanceTotale / deltaTemps;

		} else {
			//System.out
			//		.println("Device.getSpeed() : no deviceInfos in device yet");
			return 0;
		}
	}

	
	public double getSpeed(){
		System.out.println("on est dans getSpeed");
		if(deviceinfos.size() >=1){
			// System.out.println(deviceinfos.get(1).toString());
			// System.out.println(deviceinfos.get(deviceinfos.size()-1).getSpeed());
			return deviceinfos.get(deviceinfos.size()-1).getSpeed();

		}else{
			System.err.println("Appel de getSpeed alors qu'il n'y a pas de deviceinfos dans le device");
			return 0;
		}
		
	}
	
	/**
	 * @return the idDevice
	 */
	public String getIdDevice() {
		return idDevice;
	}

	/**
	 * @param idDevice
	 *            the idDevice to set
	 */
	public void setIdDevice(String idDevice) {
		this.idDevice = idDevice;
	}

	public boolean isSensHoraire() {
		return sensHoraire;
	}

	public void setSensHoraire(boolean sensHoraire) {
		this.sensHoraire = sensHoraire;
	}
 }
