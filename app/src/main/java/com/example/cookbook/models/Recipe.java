package com.example.cookbook.models;

import java.util.ArrayList;
import java.util.Map;

public class Recipe {

    private String name, description, image, instructions;
    private int likes;
    private Map<String, Ingredient> ingredients;

    public Recipe() {
    }

    public Recipe(String name, String description, String image, String instructions, int likes, Map<String, Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.instructions = instructions;
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

    public Map<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getLikes() {
        return 0;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", instructions='" + instructions + '\'' +
                ", likes=" + likes +
                ", ingredients=" + ingredients +
                '}';
    }
}
