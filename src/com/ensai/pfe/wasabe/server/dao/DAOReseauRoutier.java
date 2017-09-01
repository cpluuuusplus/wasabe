/**
 *   
 */
package com.ensai.pfe.wasabe.server.dao;
 
import java.util.ArrayList;
import java.util.List;
 
 
import com.ensai.pfe.wasabe.server.metier.Coord;
import com.ensai.pfe.wasabe.server.metier.Device;
import com.ensai.pfe.wasabe.server.route.AnneauRoutier;
import com.ensai.pfe.wasabe.server.route.Intersection;
import com.ensai.pfe.wasabe.server.route.Troncon;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
 
/**
 * @author Nicolas
 *
 */
public class DAOReseauRoutier {
 
 
    /**
     * Reads the full road network from the PERIPHERIQUE collection
     *   
     * @return
     */
    public AnneauRoutier readFromBase(){
 
       // System.out.println("on est dans le readFromBase()");
        //on commence par créer les intersections : pour ce faire on crée ue query qui va nous retourner tous les intersections
 
        ArrayList<Intersection> listeIntersection = new ArrayList<Intersection>();
 
        // on cr�e la requete
        DBObject query = new BasicDBObject("intersection","true");
        
        //cette ligne effectue la connection avec la base de données
        new DAOTroncon();
        //System.out.println(DAOTroncon.getCollectionPoints().toString());
        // on parcourt la collection de points
        DBCursor cursor = DAOTroncon.getCollectionPoints().find(query);
 
        //System.out.println("on est avant la premiere boucle");
 
        //On crée les intersections  
        while(cursor.hasNext()){
            BasicDBObject point = (BasicDBObject) cursor.next();
            //s'il s'agit d'une intersection
            String nomIntersection = point.getString("nomIntersection");
            String idPoint = point.getString("_id");
            double latitude = point.getDouble("latitude");
            double longitude = point.getDouble("longitude");
            Intersection d1 = new Intersection(idPoint, nomIntersection, new Coord(longitude, latitude));
            listeIntersection.add(d1);           
        }
       // System.out.println("on a passé la première boucle");
 
        // à ce stade on a obtenu une arraylist d'intersections  
 
        // on recupère le nombre d'intersections
        int nbIntersection = listeIntersection.size();
        //System.out.println("nbre d'intersections = "+nbIntersection);
 
        //on crée une liste de Troncons
        ArrayList<Troncon> listeTroncon = new ArrayList<Troncon>();
 
        // on parcourt la collection de points
        cursor = DAOTroncon.getCollectionTroncon().find();
 
        //On crée les troncons
        int compteur = 0;
        while(cursor.hasNext()){
            BasicDBObject t = (BasicDBObject) cursor.next();
            String idTroncon = t.getString("_id");
            double angle = t.getDouble("angle");
            double distance = t.getDouble("distance");
            double vitesseMax = t.getDouble("vitesseMax");
            List<Device> listDevice = new ArrayList<Device>();
            Troncon t1 = new Troncon(idTroncon, angle, distance, listDevice, vitesseMax);

            if((compteur+1)<listeIntersection.size()){
                t1.setintersectionSuivanteSensHoraire(listeIntersection.get(compteur+1));
            }
            else{
                t1.setintersectionSuivanteSensHoraire(listeIntersection.get(0));
            }
            t1.setIntersectionPrecedenteSensHoraire(listeIntersection.get(compteur));
            listeTroncon.add(t1);
            compteur++;
        }
 
        System.out.println("on a passé la deuxieme boucle");
        //on va créer l'anneau routier
        AnneauRoutier anneauRoutier = new AnneauRoutier(listeIntersection, listeTroncon);
 
        System.out.println("on a crée l'anneau routier");
        
        return anneauRoutier;
    }
}