package com.ensai.pfe.wasabe.server.simulation;

public class User {
    private static long counter = 0;
    private long id = counter++; // generate user ID
    private int lambda;             // send info every lambda seconds
    private int nextPointGap;       // length of iteration for the next point
    private Peripheral peripheral;
    private int startPositionIndex; // the starting position index in
    private String userId; // The identifier of the user, set by the server

    public User(int lambda, int nextPoint, Peripheral peripheral, int startPositionIndex) {
        this.lambda = lambda;
        this.nextPointGap = nextPoint;
        this.peripheral = peripheral;
        this.startPositionIndex = startPositionIndex;
        this.setUserId("");
    }

    @Override
    public int hashCode() {
        return (int) (id);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof User) && ((User) obj).id == id;
    }

    @Override
    public String toString() {
        return "id: " + id + " lambda:" + lambda + " nextPointGap:" + nextPointGap + " peripheral:" + peripheral
                + " startPositionIndex:" + startPositionIndex + "\n";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLambda() {
        return lambda;
    }

    public void setLambda(int lambda) {
        this.lambda = lambda;
    }

    public int getNextPointGap() {
        return nextPointGap;
    }

    public void setNextPointGap(int nextPointGap) {
        this.nextPointGap = nextPointGap;
    }

    public Peripheral getPeripheral() {
        return peripheral;
    }

    public void setPeripheral(Peripheral peripheral) {
        this.peripheral = peripheral;
    }

    public int getStartPositionIndex() {
        return startPositionIndex;
    }

    public void setStartPositionIndex(int startPositionIndex) {
        this.startPositionIndex = startPositionIndex;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}