package com.example.cookbook.models;

import java.util.ArrayList;

public class Recipe {

    private String name, description, image, instructions, category;
    private int likes;
    private ArrayList<Ingredient> ingredients;

    public Recipe(String name, String description, String image, String instructions, String category, int likes, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.instructions = instructions;
        this.category = category;
        this.likes = likes;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getLikes() {
        return 0;
    }
}
