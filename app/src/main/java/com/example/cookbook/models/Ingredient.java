package com.example.cookbook.models;

public class Ingredient {

    private String name;
    private String amount;
    private String units;

    public Ingredient(String name, String amount, String units) {
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

    public String getAmount() {
        return amount;
    }

    public Ingredient setAmount(String amount) {
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
}
