package com.ensai.pfe.wasabe.server.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;

import org.bson.types.ObjectId;

import com.ensai.pfe.wasabe.server.metier.DeviceInfo;
import com.ensai.pfe.wasabe.server.route.Troncon;
import com.ensai.pfe.wasabe.server.tools.CalculDistanceDirection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * Cette classe sert � enregistrer les Device (repr�sentant des t�l�phones)
 * et des DeviceInfo (repr�sentant une information ponctuelle sur le t�l�phone)
 * 
 * @author ensai
 *
 */
@Stateless
public class DAODeviceInfo {

	public static final String HOST = "localhost";
	public static final int PORT = 27017;
	public static final String BASENAME = "DEVICES";
	public static final String DEVICE = "device";
	public static final String DEVICEINFO = "deviceinfo";		


	private MongoClient client;
	private DB base;

	private DBCollection collectionDevice;// = base.getCollection("Device");
	private static DBCollection collectionDeviceInfo;// = base.getCollection("DeviceInfo");

	/**
	 * Constructeur
	 */
	public DAODeviceInfo() {
		try {

			this.client=new MongoClient(HOST, PORT);
			this.base=client.getDB(BASENAME);
			this.collectionDevice = base.getCollection(DEVICE);
			DAODeviceInfo.collectionDeviceInfo = base.getCollection(DEVICEINFO);
			// System.out.println("DAODeviceInfo constructeur : on a construit le DAODeviceInfo");
		} catch (UnknownHostException e) {
			System.out.println("DAODeviceInfo constructeur : on est dans l'exception");
			e.printStackTrace();
		}
	}


	/**
	 * Constructeur alternatif bas� sur MongoClientProvider
	 * 
	 * 
	 * 
	 * @param b booleen quelconque
	 */
	public DAODeviceInfo(boolean b){
		MongoClientProvider mcp = new MongoClientProvider();
		client = mcp.getMongoClient();
		base=client.getDB(BASENAME);
		collectionDevice = base.getCollection(DEVICE);
		collectionDeviceInfo = base.getCollection(DEVICEINFO);


	}


	/* Cette fonction est appelée quand l'on souhaite enregistrer un nouveau device.
	 * elle enregistre un nouveau device dans la collection de la base Device
	 * elle retourne l'identifiant du device qui vient d'être crée */

	/**
	 * Enregistre un nouveau device
	 * retourne l'identifiant d'un nouvau device
	 * 
	 * @return
	 */
	public String saveDevice(){
		System.out.println("on est dans saveDevice()");
		String resultat;
		BasicDBObject insertedDevice = new BasicDBObject("test", "truc");

		collectionDevice.insert(insertedDevice);
		ObjectId id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();
		System.out.println("on a recupere l'identifiant : "+resultat);
		return resultat;
	}

	/* Cette fonction est appelée quand l'on souhaite enregistrer un DeviceInfo
	 * elle crée le DBObject et l'ajoute à la collection "collectionDevice"	 
	 * elle retourne l'identifiant généré par mongodb*/

	/**
	 * 
	 * Enregistre 
	 * @param di
	 * @return
	 */
	public void saveDeviceInfo(DeviceInfo di) {
		BasicDBObject insertedDI = new BasicDBObject("tempsReleve", di.getTempsReleve());
		insertedDI.append("latitude", di.getLatitude());
		insertedDI.append("longitude", di.getLongitude());
		insertedDI.append("precision", di.getPrecision());
		if(di.getSpeed()!=-1.0){
			insertedDI.append("vitesse", di.getSpeed());
		}
		insertedDI.append("idDevice", di.getIdDevice());
		insertedDI.append("dateInsertion", new Date());	
		collectionDeviceInfo.insert(insertedDI);
	}

	/* retourne une arrayList contenant tous les deviceInfo */

	public ArrayList<DeviceInfo> loadAllDI() {
		ArrayList<DeviceInfo> dis = new ArrayList<DeviceInfo>();
		DeviceInfo di;
		DBCursor res = collectionDeviceInfo.find();
		while(res.hasNext()) {
			BasicDBObject ligne = (BasicDBObject) res.next();
			double tempsReleve = ligne.getDouble("tempsReleve");
			double latit = ligne.getDouble("latitude");
			double longit = ligne.getDouble("longitude");
			double precision=ligne.getDouble("precision");
			String idDevice = ligne.getString("idDevice");

			di = new DeviceInfo(tempsReleve, latit, longit , precision, idDevice,"");
			dis.add(di);
		}
		return dis;
	}

	/*retourne une arrayList contenant les deviceInfos d'un seul device */

	public static ArrayList<DeviceInfo> recupereDonnees(String idDevice) {
		//System.out.println("on est dans recupereDonnes");
		ArrayList<DeviceInfo> dis = new ArrayList<DeviceInfo>();
		DeviceInfo di;
		BasicDBObject query = new BasicDBObject("idDevice", idDevice);
		DBCursor res = collectionDeviceInfo.find(query);
		while(res.hasNext()) {
			BasicDBObject ligne = (BasicDBObject) res.next();
			di = new DeviceInfo(ligne.getDouble("tempsReleve"), ligne.getDouble("latitude"),
					ligne.getDouble("longitude"), ligne.getDouble("precision"), ligne.get("_id").toString(),ligne.getString("destination"));

			try{
				di.setSpeed(ligne.getDouble("vitesse"));

				// TODO Prendre que les derniers termes

			}catch(NullPointerException e){

			}
			dis.add(di);
		}
System.out.println(dis);
		return dis;
	}

	public static void calculVitesse(DeviceInfo di){
		double vitesse;
		DeviceInfo di2 = lastDeviceInfo(di);
		vitesse = CalculDistanceDirection.calculateVitesse(di.getLatitude(), di.getLongitude(), di2.getLatitude(),
				di2.getLongitude(), di2.getTempsReleve(), di.getTempsReleve());
		di.setSpeed(vitesse);

	}
	public static boolean isSensHoraire(DeviceInfo di, Troncon t){
		double angle;
		DeviceInfo di2 = lastDeviceInfo(di);
		angle = CalculDistanceDirection.calculateBearing(di.getLatitude(), di.getLongitude(), di2.getLatitude(),
				di2.getLongitude());
		return (Math.abs(angle-t.getAngle())<90);		 
	}
	public static DeviceInfo lastDeviceInfo(DeviceInfo di){
		ArrayList<DeviceInfo> liste = new ArrayList<DeviceInfo>();
		liste = recupereDonnees(di.getIdDevice());
		int i = liste.size();
		DeviceInfo di2 = liste.get(i-1);
		return di2;
	}
}
