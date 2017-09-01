package com.ensai.pfe.wasabe.server.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ensai.pfe.wasabe.server.metier.Coord;
import com.ensai.pfe.wasabe.server.metier.DeviceInfo;
import com.ensai.pfe.wasabe.server.metier.Point;
import com.ensai.pfe.wasabe.server.tools.CalculDistanceDirection;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DAOTroncon {

	private static MongoClient client;
	private static DB base;

	private static DBCollection collectionTroncon;
	private static DBCollection collectionPoints;

	public static void main(String[] args) {

		// Initialization
		try {
			client = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		base = client.getDB("troncons");
		collectionTroncon = base.getCollection("troncons");
		collectionPoints = base.getCollection("points");
		collectionTroncon.drop();
		collectionPoints.drop();
		// on insère le premier troncon : il a un nom et l'id donné par mongo
		String resultat;
		BasicDBObject insertedDevice = new BasicDBObject("nom",
				"Alma-Brequigny");
		insertedDevice.append("angle", 262);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("distance", 1.4);
		collectionTroncon.insert(insertedDevice);
		ObjectId id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		// on insère le deuxieme troncon : il a un nom, l'id donné par mongodb
		// et l'id du troncon précédent
		String resultat2;
		BasicDBObject insertedDevice2 = new BasicDBObject("nom",
				"Brequigny-StNazaire");
		insertedDevice2.append("TRPR", resultat);
		insertedDevice2.append("angle", 327);
		insertedDevice2.append("vitesseMax", 90);
		insertedDevice2.append("distance", 1.5);
		collectionTroncon.insert(insertedDevice2);
		ObjectId id2 = (ObjectId) insertedDevice2.get("_id");
		resultat2 = id2.toString();

		// on recupère l'id du dernier troncon inséré et on l'ajoute au
		// troncon d'avant
		DBCursor curseur = collectionTroncon.find(insertedDevice);
		DBObject insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat2);
		collectionTroncon.update(insertedDevice, insertedDeviceU);

		// on insère le troisieme troncon
		insertedDevice = new BasicDBObject("nom", "StNazaire-Cleunay");
		insertedDevice.append("TRPR", resultat2);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("angle", 322);
		insertedDevice.append("distance", 1.6);
		collectionTroncon.insert(insertedDevice);
		id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		// on recupere l'id du 3eme troncon et on l'ajoute au deuxieme
		curseur = collectionTroncon.find(insertedDevice2);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat);
		collectionTroncon.update(insertedDevice2, insertedDeviceU);

		// 4eme
		insertedDevice2 = new BasicDBObject("nom", "Cleunay-Lorient");
		insertedDevice2.append("TRPR", resultat);
		insertedDevice2.append("angle", 349);
		insertedDevice2.append("vitesseMax", 90);
		insertedDevice2.append("distance", 0.6);
		collectionTroncon.insert(insertedDevice2);
		id2 = (ObjectId) insertedDevice2.get("_id");
		resultat2 = id2.toString();

		//
		curseur = collectionTroncon.find(insertedDevice);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat2);
		collectionTroncon.update(insertedDevice, insertedDeviceU);

		// on insère le 5eme troncon
		insertedDevice = new BasicDBObject("nom", "Lorient-Brest");
		insertedDevice.append("TRPR", resultat2);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("angle", 352);
		insertedDevice.append("distance", 1.4);
		collectionTroncon.insert(insertedDevice);
		id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		//
		curseur = collectionTroncon.find(insertedDevice2);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat);
		collectionTroncon.update(insertedDevice2, insertedDeviceU);

		// 6eme
		insertedDevice2 = new BasicDBObject("nom", "Brest-Villejean");
		insertedDevice2.append("TRPR", resultat);
		insertedDevice2.append("angle", 25);
		insertedDevice2.append("vitesseMax", 90);
		insertedDevice2.append("distance", 0.65);
		collectionTroncon.insert(insertedDevice2);
		id2 = (ObjectId) insertedDevice2.get("_id");
		resultat2 = id2.toString();

		//
		curseur = collectionTroncon.find(insertedDevice);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat2);
		collectionTroncon.update(insertedDevice, insertedDeviceU);

		// on insère le 7eme troncon
		insertedDevice = new BasicDBObject("nom", "Villejean-StMalo");
		insertedDevice.append("TRPR", resultat2);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("angle", 42);
		insertedDevice.append("distance", 2.3);
		collectionTroncon.insert(insertedDevice);
		id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		//
		curseur = collectionTroncon.find(insertedDevice2);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat);
		collectionTroncon.update(insertedDevice2, insertedDeviceU);

		// 8eme
		insertedDevice2 = new BasicDBObject("nom", "StMalo-Maurepas");
		insertedDevice2.append("TRPR", resultat);
		insertedDevice2.append("angle", 85);
		insertedDevice2.append("vitesseMax", 90);
		insertedDevice2.append("distance", 2.5);
		collectionTroncon.insert(insertedDevice2);
		id2 = (ObjectId) insertedDevice2.get("_id");
		resultat2 = id2.toString();

		//
		curseur = collectionTroncon.find(insertedDevice);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat2);
		collectionTroncon.update(insertedDevice, insertedDeviceU);

		// on insère le 9eme troncon
		insertedDevice = new BasicDBObject("nom", "Maurepas-LongChamps");
		insertedDevice.append("TRPR", resultat2);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("angle", 94);
		insertedDevice.append("distance", 2.7);
		collectionTroncon.insert(insertedDevice);
		id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		//
		curseur = collectionTroncon.find(insertedDevice2);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat);
		collectionTroncon.update(insertedDevice2, insertedDeviceU);

		// 10eme
		insertedDevice2 = new BasicDBObject("nom", "LongChamps-Normandie");
		insertedDevice2.append("TRPR", resultat);
		insertedDevice2.append("angle", 61);
		insertedDevice2.append("vitesseMax", 90);
		insertedDevice2.append("distance", 2.2);
		collectionTroncon.insert(insertedDevice2);
		id2 = (ObjectId) insertedDevice2.get("_id");
		resultat2 = id2.toString();

		//
		curseur = collectionTroncon.find(insertedDevice);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat2);
		collectionTroncon.update(insertedDevice, insertedDeviceU);

		// on insère le 11eme troncon
		insertedDevice = new BasicDBObject("nom", "Normandie-Tize");
		insertedDevice.append("TRPR", resultat2);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("angle", 126);
		insertedDevice.append("distance", 1.9);
		collectionTroncon.insert(insertedDevice);
		id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		//
		curseur = collectionTroncon.find(insertedDevice2);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat);
		collectionTroncon.update(insertedDevice2, insertedDeviceU);

		// 12eme
		insertedDevice2 = new BasicDBObject("nom", "Tize-Rigourdiere");
		insertedDevice2.append("TRPR", resultat);
		insertedDevice2.append("angle", 162);
		insertedDevice2.append("vitesseMax", 90);
		insertedDevice2.append("distance", 2.8);
		collectionTroncon.insert(insertedDevice2);
		id2 = (ObjectId) insertedDevice2.get("_id");
		resultat2 = id2.toString();

		//
		curseur = collectionTroncon.find(insertedDevice);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat2);
		collectionTroncon.update(insertedDevice, insertedDeviceU);

		// on insère le 13eme troncon
		insertedDevice = new BasicDBObject("nom", "Rigourdiere-Valette");
		insertedDevice.append("TRPR", resultat2);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("angle", 252);
		insertedDevice.append("distance", 1.2);
		collectionTroncon.insert(insertedDevice);
		id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		//
		curseur = collectionTroncon.find(insertedDevice2);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat);
		collectionTroncon.update(insertedDevice2, insertedDeviceU);

		// 14eme
		insertedDevice2 = new BasicDBObject("nom", "Valette-Beaulieu");
		insertedDevice2.append("TRPR", resultat);
		insertedDevice2.append("angle", 238);
		insertedDevice2.append("vitesseMax", 90);
		insertedDevice2.append("distance", 1.9);
		collectionTroncon.insert(insertedDevice2);
		id2 = (ObjectId) insertedDevice2.get("_id");
		resultat2 = id2.toString();

		//
		curseur = collectionTroncon.find(insertedDevice);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat2);
		collectionTroncon.update(insertedDevice, insertedDeviceU);

		// on insère le 15eme troncon
		insertedDevice = new BasicDBObject("nom", "Beaulieu-Loges");
		insertedDevice.append("TRPR", resultat2);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("angle", 230);
		insertedDevice.append("distance", 1.8);
		collectionTroncon.insert(insertedDevice);
		id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		//
		curseur = collectionTroncon.find(insertedDevice2);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat);
		collectionTroncon.update(insertedDevice2, insertedDeviceU);

		// 16eme
		insertedDevice2 = new BasicDBObject("nom", "Loges-Angers");
		insertedDevice2.append("TRPR", resultat);
		insertedDevice2.append("vitesseMax", 90);
		insertedDevice2.append("angle", 232);
		insertedDevice2.append("distance", 1.4);
		collectionTroncon.insert(insertedDevice2);
		id2 = (ObjectId) insertedDevice2.get("_id");
		resultat2 = id2.toString();

		//
		curseur = collectionTroncon.find(insertedDevice);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat2);
		collectionTroncon.update(insertedDevice, insertedDeviceU);

		// on insère le 17eme troncon
		insertedDevice = new BasicDBObject("nom", "Angers-Alma");
		insertedDevice.append("TRPR", resultat2);
		insertedDevice.append("vitesseMax", 90);
		insertedDevice.append("angle", 262);
		insertedDevice.append("distance", 2.5);
		collectionTroncon.insert(insertedDevice);
		id = (ObjectId) insertedDevice.get("_id");
		resultat = id.toString();

		//
		curseur = collectionTroncon.find(insertedDevice2);
		insertedDeviceU = curseur.next();
		insertedDeviceU.put("TRS", resultat);
		collectionTroncon.update(insertedDevice2, insertedDeviceU);

		// dernier truc tricky :
		// on recup�re le premier tron�on ins�r�: il s'appelle insertedDevice2

		BasicDBObject query = new BasicDBObject("nom", "Alma-Brequigny");
		DBCursor cursor = collectionTroncon.find(query);
		insertedDevice2 = (BasicDBObject) cursor.next();

		// on copie ce tron�on et on ajoute l'identifiant du tron�on pr�c�dent
		// (i.e le dernier ins�r� dans la base)
		BasicDBObject insertedDevice3 = insertedDevice2;
		insertedDevice3.put("TRPR", resultat);

		// on update
		collectionTroncon.update(query, insertedDevice3);

		// on veut dire au dernier tron�on ins�r� que l'id du tron�on pr�c�dent
		// TRPR est celui du premier tron�on ins�r�
		// on recupere le dernier tron�on ins�r�, il s'appelle insertedDeviceU
		query = new BasicDBObject("nom", "Angers-Alma");
		curseur = collectionTroncon.find(query);
		insertedDevice3 = (BasicDBObject) curseur.next();

		// on copie ce tron�on et on ajoute l'identifiant du tron�on suivat
		BasicDBObject insertedDevice4 = insertedDevice3;
		insertedDevice4.put("TRS", insertedDevice2.get("_id"));

		collectionTroncon.update(query, insertedDevice4);

		//System.out.println("on a fini !!!");

		// System.out.println("un premier essai");
		try {
			writeJSON("Alma-Brequigny","6");
			writeJSON("Brequigny-StNazaire","7");
			writeJSON("StNazaire-Cleunay","8");
			writeJSON("Cleunay-Lorient","9");
			writeJSON("Lorient-Brest","10");
			writeJSON("Brest-Villejean","11");
			writeJSON("Villejean-StMalo","12");
			writeJSON("StMalo-Maurepas","13");
			writeJSON("Maurepas-LongChamps","14");
			writeJSON("LongChamps-Normandie","15");
			writeJSON("Normandie-Tize","16");
			writeJSON("Tize-Rigourdiere","17");
			writeJSON("Rigourdiere-Valette","1");
			writeJSON("Valette-Beaulieu","2");
			writeJSON("Beaulieu-Loges","3");
			writeJSON("Loges-Angers","4");
			writeJSON("Angers-Alma","5");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("collectionPoints : " + collectionPoints.toString());
		//System.out
		//		.println("collectionPoints : " + collectionTroncon.toString());
	}

	public DAOTroncon() {
		try {

			client = new MongoClient("localhost", 27017);
			base = client.getDB("troncons");
			collectionTroncon = base.getCollection("troncons");
			collectionPoints = base.getCollection("points");
			//System.out.println("on a construit le DAOTroncon");
		} catch (UnknownHostException e) {
			//System.out.println("on est dans l'exception");
			e.printStackTrace();
		}
	}

	public static String readJsonFromUrl(String nomFichier) throws IOException,
			JSONException {
		nomFichier = "Troncons/" + nomFichier;
		try {
			BufferedReader rd = new BufferedReader(new FileReader(new File(
					nomFichier)));
			String jsonText = readAll(rd);
			//System.out.println(jsonText);
			return jsonText;
		} finally {
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static void writeJSON(String nomTroncon, String nomIntersection)
			throws JSONException, IOException {
		//System.out.println("on est dans writeJSON pour le tron�on "
		//		+ nomTroncon);
		JSONObject truc = new JSONObject(readJsonFromUrl(nomTroncon));
		String coordinates = truc.get("coordinates").toString();
		JSONArray array = new JSONArray(coordinates);

		BasicDBObject query = new BasicDBObject("nom", nomTroncon);
		DBCursor cursor = collectionTroncon.find(query);
		BasicDBObject tronconLoaded = (BasicDBObject) cursor.next();
		String idTroncon = tronconLoaded.getString("_id");

		// on remplit la collection

		for (int i = 0; i < array.length(); i++) {
			JSONArray aaa = (JSONArray) array.get(i);
			BasicDBObject dbObject = new BasicDBObject("idTroncon", idTroncon);
			dbObject.append("longitude", aaa.get(0));
			dbObject.append("latitude", aaa.get(1));
			if (i == 0) {
				dbObject.append("intersection", "true");
				dbObject.append("nomIntersection", nomIntersection);
			} else {
				dbObject.append("intersection", "false");
			}
			collectionPoints.insert(dbObject);
		}
		System.out.println("on sort du writeJSON");
	}

	public static ArrayList<Point> isOnPeriph(DeviceInfo di) {
		// la pr�cision de la requ�te
		ArrayList<Point> resultat = new ArrayList<Point>();
		double approximation = 0.01;

		// on cr�e 2 requ�tes
		BasicDBObject query = new BasicDBObject("latitude", new BasicDBObject(
				"$gt", di.getLatitude() - approximation).append("$lte",
						di.getLatitude() + approximation));
		BasicDBObject query2 = new BasicDBObject("longitude",
				new BasicDBObject("$gt", di.getLongitude() - approximation)
		.append("$lte", di.getLongitude() + approximation));

		// on regroupe les deux requ�tes afin de n'en faire qu'une seule
		BasicDBList listequery = new BasicDBList();
		listequery.add(query);
		listequery.add(query2);
		BasicDBObject query3 = new BasicDBObject("$and", listequery);

		// on parcourt la collection de points
		DBCursor cursor = getCollectionPoints().find(query3);

		try {
			while (cursor.hasNext()) {
				BasicDBObject dbo = (BasicDBObject) cursor.next();
				Point p = null;
				p = new Point(dbo.getString("_id"), new Coord(
						dbo.getDouble("latitude"),dbo.getDouble("longitude")),
						dbo.getString("idTroncon"));
				resultat.add(p);
			}
		} finally {
			cursor.close();
		}

		return resultat;

	}

	public static Point pointLePlusProche(ArrayList<Point> liste, DeviceInfo di) {
		double distanceMin = 5000; // TODO fixer l'approximation maximale
		Point point = new Point();
		for (int i = 0; i < liste.size(); i++) {
			// System.out.println("i " + i);
			double distance = CalculDistanceDirection.calculateDistance(
					di.getLatitude(), di.getLongitude(), liste.get(i)
							.getCoord().getLat(), liste.get(i).getCoord()
							.getLong());
			// System.out.println(distance);
			if (distance <= distanceMin) {
				point = liste.get(i);
				distanceMin = distance;
			}
		}

		return point;

	}

	public static DBCollection getCollectionPoints() {
		return collectionPoints;
	}

	public static void setCollectionPoints(DBCollection collectionPoints) {
		DAOTroncon.collectionPoints = collectionPoints;
	}

	public static DBCollection getCollectionTroncon() {
		return collectionTroncon;
	}

	public static void setCollectionTroncon(DBCollection collectionTroncon) {
		DAOTroncon.collectionTroncon = collectionTroncon;
	}

}