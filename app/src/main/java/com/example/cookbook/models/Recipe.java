package com.example.cookbook.models;

import java.util.ArrayList;

public class Recipe {

    private String name, description, image, instructions, categoryId;
    private int likes;
    private ArrayList<Ingredient> ingredients;

    public Recipe() {
    }

    public Recipe(String name, String description, String image, String instructions, String categoryId, int likes, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.instructions = instructions;
        this.categoryId = categoryId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
