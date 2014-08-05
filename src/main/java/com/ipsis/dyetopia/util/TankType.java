package com.ipsis.dyetopia.util;

public enum TankType {

    RED("redTank"),
    YELLOW("yellowTank"),
    BLUE("blueTank"),
    WHITE("whiteTank"),
    PURE("pureTank");

    private String name;
    private TankType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
