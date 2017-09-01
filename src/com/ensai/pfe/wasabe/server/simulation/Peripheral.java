package com.ensai.pfe.wasabe.server.simulation;

public enum Peripheral {
    INNER(1), OUTER(0);

    private int arrayInt;

    private Peripheral(int arrayInt) {
        this.arrayInt = arrayInt;
    }

    // method helps to get the inner/outer peripheral of the array
    public int getArrayInt() {
        return arrayInt;
    }

    // test
    public static void main(String[] args) {
        for (Peripheral periph : Peripheral.values()) {
            System.out.println(periph + " has array index: " + periph.getArrayInt());
        }
    }
}