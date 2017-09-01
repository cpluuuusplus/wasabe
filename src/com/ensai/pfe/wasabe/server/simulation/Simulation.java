package com.ensai.pfe.wasabe.server.simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

public class Simulation extends TimerTask {
	
	
	
	// test
	public static void main(String[] args) {
		runSimulation(50);
	}
	

	private static double[][][] data = JsonToArray.data;
	private static List<User> users = new ArrayList<User>();
	private static List<Integer> comptes = new ArrayList<>();
	private static Random random = new Random(47);

	/**
     * 
     * 
     * 
     */
	@Override
	public void run() {
		// System.out.println(comptes);
		for (int i = 0; i < comptes.size(); i++) {
			// get the user
			User user = users.get(i);
			// iterate to the next position
			int positionIndex = user.getStartPositionIndex();
			int arrayInt = user.getPeripheral().getArrayInt();
			if (positionIndex + user.getNextPointGap() >= data[arrayInt].length) {
				// ! be careful about the index
				user.setStartPositionIndex(positionIndex
						+ user.getNextPointGap() - data[arrayInt].length);
			} else {
				user.setStartPositionIndex(positionIndex
						+ user.getNextPointGap());
			}

			int compte = comptes.get(i);
			comptes.set(i, --compte);
			// examine if compte is 0
			if (compte == 0) {
				double[] position = data[arrayInt][positionIndex];

				// userid is from mongo, id is user number
				String userId = sendDeviceInfo(user.getUserId(), user.getId(),
						position[0], position[1]);
				if (!userId.isEmpty()) {
					user.setUserId(userId);

				}
				// reset the compte value to lambda
				comptes.set(i, user.getLambda());
			}

		}
	}

	private static void createSingleUser(int lambda, int nextPointGap,
			Peripheral peripheral) {
		// get the index of the peripheral in the data array
		int arrayIndex = peripheral.getArrayInt();
		// generate random starting position
		int startPositionIndex = random.nextInt(data[arrayIndex].length);
		User user = new User(lambda, nextPointGap, peripheral,
				startPositionIndex);
		users.add(user);
	}

	private static void createUsers(int numberOfUsers) {
		for (int i = 0; i < numberOfUsers; i++) {
			int lambda = random.nextInt(10) + 1; // 1-20
			// 1-3, an alternative is to create an map of <lambda,nextPointGap>
			int nextPointGap = lambda / 8 + 1;
			// random peripheral, can also be fixed for simulation
			Peripheral peripheral = (random.nextDouble() > 0.5) ? (Peripheral.INNER)
					: (Peripheral.OUTER);
			// create this user
			createSingleUser(lambda, nextPointGap, peripheral);
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param id
	 * @param longitude
	 * @param lattitude
	 * @throws IOException
	 */
	private static String sendDeviceInfo(String idFromMongo, long id,
			double longitude, double lattitude) {

		String deviceInfoStringedJson = "";
		if (idFromMongo.isEmpty()) {
			deviceInfoStringedJson = "{ \"deviceInfo\": { \"latitude\": "
					+ lattitude + ", \"longitude\": " + longitude
					+ ", \"precision\": 20, \"id\": \"\", \"destination\": \" "
					+ 5 + "\", \"temps\":" + new Date().getTime() / 1000
					+ " } }";
		} else {
			deviceInfoStringedJson = "{ \"deviceInfo\": { \"latitude\": "
					+ lattitude + ", \"longitude\": " + longitude
					+ ", \"precision\": 20, \"id\":\"" + idFromMongo
					+ "\", \"destination\": \"" + 5 + "\", \"temps\":"
					+ new Date().getTime() / 1000 + " } }";

		}
		String result = "";
		try {
			// Appeler StringToServer pour avoir l'identifiant
			result = StringToServer.stringToServer(deviceInfoStringedJson);
		} catch (IOException e) {
			System.out.println("IO exception in sendDeviceInfo ");
			e.printStackTrace();
		}
		String identifiant = "";

		if (!result.isEmpty()) {
			if (result.contains("identifiant")) {
				// Il s'agit de la réponse identifiant
				JSONObject jsonId;
				try {
					jsonId = new JSONObject(result);
					identifiant = jsonId.getString("identifiant");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return identifiant;

	}

	private static void runSimulation(int numberOfUsers) {
		createUsers(numberOfUsers); // create users
		for (User user : users) {
			comptes.add(user.getLambda());
		}

		// begin simulation
		TimerTask simulation = new Simulation();
		// running timer task as daemon thread
		Timer timer = new Timer(true);
		// schedule 1000 milleseconds
		timer.scheduleAtFixedRate(simulation, 0, 1 * 1000);
		System.out.println("TimerTask started");
		// cancel after sometime
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		System.out.println("TimerTask cancelled");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



}