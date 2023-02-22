package com.example.cookbook.models;

public class Ingredient {

    private String name;
    private double amount;
    private String units;

    public Ingredient() {
    }

    public Ingredient(String name, double amount, String units) {
        this.name = name;
        this.amount = amount;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public Ingredient setName(String name) {
        this.name = name;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Ingredient setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getUnits() {
        return units;
    }

    public Ingredient setUnits(String units) {
        this.units = units;
        return this;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", units='" + units + '\'' +
                '}';
    }
}
