package com.ensai.pfe.wasabe.server.dao;


import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.mongodb.MongoClient;

/****
 * 
 * 
 * Cette classe permet à la DAO de s'injecter une dépendance de Mongo
 * 
 * 
 * @author ensai
 *
 */

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoClientProvider {
		
	private MongoClient mongoClient = null;
		
	@Lock(LockType.READ)
	public MongoClient getMongoClient(){	
		return mongoClient;
	}
	
	@PostConstruct
	public void init() {
		String mongoIpAddress = "127.0.0.1";
		Integer mongoPort = 27017;
		try {
			mongoClient = new MongoClient(mongoIpAddress, mongoPort);
		} catch (UnknownHostException e) {
			System.err.println("MongoClientProvider : unknown host, stacktrace incoming");
			e.printStackTrace();
		}		
	}
		
}