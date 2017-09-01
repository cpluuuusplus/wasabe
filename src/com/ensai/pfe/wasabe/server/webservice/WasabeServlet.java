package com.ensai.pfe.wasabe.server.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.ensai.pfe.wasabe.server.dao.DAODeviceInfo;
import com.ensai.pfe.wasabe.server.dao.DAOReseauRoutier;
import com.ensai.pfe.wasabe.server.dao.DAOTroncon;
import com.ensai.pfe.wasabe.server.metier.Device;
import com.ensai.pfe.wasabe.server.metier.DeviceInfo;
import com.ensai.pfe.wasabe.server.metier.Point;
import com.ensai.pfe.wasabe.server.route.AnneauRoutier;
import com.ensai.pfe.wasabe.server.route.Itineraire;
import com.ensai.pfe.wasabe.server.route.ItineraireEnvoye;
import com.ensai.pfe.wasabe.server.route.Troncon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;


public class WasabeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static AnneauRoutier ar=null;

	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";


	/**7
	 * 
	 * 
	 * Fait a l'initialisation du serveur
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		ar = new AnneauRoutier();
		DAOReseauRoutier dar = new DAOReseauRoutier();
		ar = dar.readFromBase();

	}

	/**
	 * Process the HTTP doGet request.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();

		// On instancie si il n'existe pas deja
		if(context.getAttribute("AnneauRoutier")==null){
			context.setAttribute("AnneauRoutier", ar);

		}


		// On va chercher et on modifie  l'anneau routier
		AnneauRoutier anneauAModifie = (AnneauRoutier) context.getAttribute("AnneauRoutier");


		List<com.ensai.pfe.wasabe.server.route.Intersection> is = anneauAModifie.getIntersections();
		is.remove(0);
		anneauAModifie.setIntersections(is);
		context.setAttribute("AnneauRoutier", anneauAModifie);


		// On verifie que la modification est effectivement là
		AnneauRoutier lAnneauQuiDevraitEtreModifie = (AnneauRoutier) context.getAttribute("AnneauRoutier");
		String laPreuveQueCaMarche = lAnneauQuiDevraitEtreModifie.getIntersections().toString();

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>demolet</title></head>");
		out.println("<body>");
		//         out.println("<p>The servlet has received a GET. This is the reply."+(String) context.getAttribute("unString")+"</p>");

		out.println("<p> Avant : "+anneauAModifie.getIntersections().toString() +"</p>");
		out.println("<br>");

		out.println("<p> Apres : "+laPreuveQueCaMarche +"</p>");

		out.println("</body></html>");
		out.close();
	}

	/**
	 * Process the HTTP doPost request.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAODeviceInfo dao = new DAODeviceInfo();
		// On instancie si il n'existe pas deja
		if(context.getAttribute("AnneauRoutier")==null){
			context.setAttribute("AnneauRoutier", ar);
		}

		// TODO : Parser le xml en device info
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { /*report an error*/ }

		JSONObject jsonObjectTotal;
		JSONObject jsonObject;

		try {

			jsonObjectTotal = new JSONObject(jb.toString());
			jsonObject = jsonObjectTotal.getJSONObject("deviceInfo");

			DeviceInfo di = new DeviceInfo();
			double latitude = jsonObject.getDouble("latitude");
			double longitude = jsonObject.getDouble("longitude");	
			double precision = jsonObject.getDouble("precision");	
			double temps = jsonObject.getDouble("temps");
			String idDevice = null;
			String destination = null;

			idDevice = jsonObject.optString("id","default");
			destination = jsonObject.optString("destination","default");

			di.setLatitude(latitude);
			di.setLongitude(longitude);
			di.setDestination(destination);
			di.setPrecision(precision);
			di.setIdDevice(idDevice);
			di.setTempsReleve(temps);
			
			
			// Le device a t il un identifiant ?
			if(idDevice.isEmpty()){
				System.out.println("notre device n'a pas d'identifiant");
				//on sauvegarde le device et le device info dans la base mongo
				idDevice = dao.saveDevice();
				di.setIdDevice(idDevice);
				dao.saveDeviceInfo(di);
				//TODO : ecrire un json ou un xml contenant l'identifiant
				 response.setContentType("application/json");
				 JSONObject json = new JSONObject();
				 json.put("identifiant", idDevice);
				 
				    PrintWriter out = response.getWriter();
				    out.println(json) ; 
				    out.close();
			}
			else{
				System.out.println("notre device a un identifiant");
				ArrayList<Point> liste = new ArrayList<Point>();
				liste =DAOTroncon.isOnPeriph(di);
				//on regarde si il est sur le periphérique
				if(liste.size()!=0){ //s'il est sur le peripherique

					
					
					// on calcule la vitesse
					DAODeviceInfo.calculVitesse(di);
					System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
					System.out.println(di.getSpeed());

					//on sauvegarde le device et le device info dans la base mongo
					dao.saveDeviceInfo(di);
					
					// on regarde quel est le point le plus proche
					Point p = new Point();
					p=DAOTroncon.pointLePlusProche(liste, di);

					//on regarde sur quel troncon se trouve le device
					ObjectId IDToncon = new ObjectId(p.getIdTroncon());
					BasicDBObject query = new BasicDBObject();
					query.put("_id", IDToncon);
					new DAOTroncon();
					DBCursor cursor = DAOTroncon.getCollectionTroncon().find(query);

					BasicDBObject troncon = (BasicDBObject) cursor.next();
					String id1 = troncon.getString("_id");
					double angle = troncon.getDouble("angle");
					double distance = troncon.getDouble("distance");
					List<Device> listDevice = new ArrayList<Device>();
					double vitesseMax = troncon.getDouble("vitesseMax");
					Troncon t = new Troncon(id1, angle, distance, listDevice, vitesseMax);


					//on recupere la liste des devices info du device
					List<DeviceInfo> lesDeviceInfo = new ArrayList<DeviceInfo>();
					lesDeviceInfo = DAODeviceInfo.recupereDonnees(idDevice);

					System.out.println("66666666666666666666666666666666666666666666666666666666666666666666666");
					System.out.println(lesDeviceInfo.toString());
					
					
					//on crée le device
					Device device = new Device(idDevice, lesDeviceInfo, DAODeviceInfo.isSensHoraire(di, t));

					ar.rajouterDevice(device, id1, di);
					System.out.println(ar.getTroncons());
					System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
					System.out.println(di.getSpeed());

					if(destination.isEmpty()){
						System.out.println("notre device n'a pas de destination");
					}
					else{
						JSONObject json = new JSONObject();
						// TODO : faire une fonction qui retourne un itinéraire
						System.out.println("notre device a une destination ");
						Itineraire it = ar.getItineraire(destination, p.getIdTroncon(), device.isSensHoraire());
						//TODO : retourner le json ou le xml qui va bien
						ItineraireEnvoye ite = new ItineraireEnvoye();
						ite = it.fromItineraireToItineraireEnvoye(device.isSensHoraire(),di);
						System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
						System.out.println(di.getSpeed());
						json = ecriture(json,ite);
						System.out.println(json);
						PrintWriter out = response.getWriter();
					    out.println(json) ; 
					    out.close();
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public JSONObject ecriture(JSONObject json, ItineraireEnvoye itineraire){
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson  = gsonBuilder.create();
			try {
				json = new JSONObject(gson.toJson(itineraire));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		return json;
	}
}




//DAODeviceInfo dao = new DAODeviceInfo();

//System.out.println("Create appelé sur "+ di.toString());

/* On regarde si le DeviceInfo reçu possède un identifiant Device  
 * si c'est le cas, alors on rajoute une ligne à la collection de device info
 * sinon, on rajoute d'abord une ligne à la collection de device */

/*if(di.getIdDevice()==null){
   			//on sauvegarde le device et le device info dans la base mongo
   			String idDevice = dao.saveDevice();
   			di.setIdDevice(idDevice);
   		}
   		else{//si c'est pas la premiere fois que l'on recoit un device info pour ce device
   			ArrayList<Point> liste = new ArrayList<Point>();
   			liste =DAOTroncon.isOnPeriph(di);
   			//on regarde si il est sur le periphérique
   			if(liste.size()!=0){ //s'il est sur le peripherique

   				// on calcule la vitesse
   				DAODeviceInfo.calculVitesse(di);

   				// on regarde quel est le point le plus proche
   				Point p = new Point();
   				p=DAOTroncon.pointLePlusProche(liste, di);

   				//on regarde sur quel troncon se trouve le device
   				ObjectId IDToncon = new ObjectId(p.getIdTroncon());
   				BasicDBObject query = new BasicDBObject();
   				query.put("_id", IDToncon);
   				DAOTroncon daoTroncon = new DAOTroncon();
   				DBCursor cursor = DAOTroncon.getCollectionTroncon().find(query);

   				BasicDBObject troncon = (BasicDBObject) cursor.next();
   				String id = troncon.getString("_id");
   				double angle = troncon.getDouble("angle");
   				double distance = troncon.getDouble("distance");
   				List<Device> listDevice = new ArrayList<Device>();
   				double vitesseMax = troncon.getDouble("vitesseMax");
   				Troncon t = new Troncon(id, angle, distance, listDevice, vitesseMax);

   				//on sauvegarde le device et le device info dans la base mongo
   				String idDevice = dao.saveDevice();
   				di.setIdDevice(idDevice);
   				dao.saveDeviceInfo(di);

   				//on recupere la liste des devices info du device
   				List<DeviceInfo> lesDeviceInfo = new ArrayList<DeviceInfo>();
   				lesDeviceInfo = DAODeviceInfo.recupereDonnees(idDevice);

   				//on crée le device
   				Device device = new Device(idDevice, lesDeviceInfo, DAODeviceInfo.isSensHoraire(di, t));

   				// TODO appeler la fonction ajouter au tronçon (idDevice, liste de device info)
   				//TODO modifier cette partie dans le nouveau web-service
   				AnneauRoutier anneauRoutier = new AnneauRoutier();
   				anneauRoutier.rajouterDevice(device, id);



   			}
   			else{
   				System.out.println("notre device info n'est pas sur le peripherique");
   			}

   		}

   		// HTTP 201 objet cr�e
   		return Response.status(201).entity(di).build();
   	}


        String var0show = "";
        try {
            var0show = request.getParameter("showthis");
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>demolet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a POST. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }*/
