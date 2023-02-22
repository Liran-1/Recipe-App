package com.example.cookbook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cookbook.models.Category;
import com.example.cookbook.models.Ingredient;
import com.example.cookbook.models.Recipe;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecipeSP {

    private static final String DB_FILE = "DB_FILE";
    private static final String RECIPE_RECORDS = "RECIPE_RECORDS";
    private static final String CATEGORY_RECORDS = "CATEGORY_RECORDS";
    public static final String CREATE_NAME = "CREATE_NAME";
    public static final String CREATE_DESCRIPTION = "CREATE_DESCRIPTION";
    public static final String CREATE_INSTRUCTIONS = "CREATE_INSTRUCTIONS";
    public static final String CREATE_INGREDIENTS = "CREATE_INGREDIENTS";
    public static final String CATEGORY_CHOSEN = "CATEGORY_CHOSEN";
    public static final String CATEGORY_CHOSEN_NUM = "CATEGORY_CHOSEN_NUM";
    public static final String RECIPE_CHOSEN = "RECIPE_CHOSEN";
    public static final String RECIPE_CHOSEN_NUM = "RECIPE_CHOSEN_NUM";


    private static RecipeSP instance = null;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private RecipeSP(Context context) {
        preferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (instance == null)
            instance = new RecipeSP(context);
    }

    public static RecipeSP getInstance() {
        return instance;
    }

    public ArrayList<Ingredient> getIngredients(String key) {
        TypeToken<List<Ingredient>> listType = new TypeToken<List<Ingredient>>() {};
        Gson gson = new Gson();
        String recipeData = preferences.getString(RECIPE_RECORDS, "");
        ArrayList<Ingredient> Ingredients = gson.fromJson(recipeData, listType.getType());
        Log.d("LOADED FILE", recipeData);
        return Ingredients != null ? Ingredients : new ArrayList<>();
    }

    public void putIngredients(String key, ArrayList<Ingredient> currentIngredients) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Gson gson = new Gson();
        for (Ingredient ingredient : currentIngredients)
            ingredients.add(ingredient);
        String json = gson.toJson(ingredients);
        editor = preferences.edit();
        editor.putString(key, json);
        editor.commit();
        Log.d("SAVED_FILE", json);
    }

    public ArrayList<Category> getCategories() {
        TypeToken<List<Category>> listType = new TypeToken<List<Category>>() {};
        Gson gson = new Gson();
        String recipeData = preferences.getString(CATEGORY_RECORDS, "");
        ArrayList<Category> categories = gson.fromJson(recipeData, listType.getType());
        Log.d("LOADED FILE", recipeData);
        return categories != null ? categories : new ArrayList<>();
    }

    public void putCategories(String key, ArrayList<Category> currentCategories) {
        ArrayList<Category> categories = new ArrayList<>();
        Gson gson = new Gson();
        for (Category category : currentCategories)
            categories.add(category);
        String json = gson.toJson(categories);
        editor = preferences.edit();
        editor.putString(key, json);
        editor.commit();
        Log.d("SAVED_FILE", json);
    }

    public void putRecipe(String key ,Recipe recipe){
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        editor = preferences.edit();
        editor.putString(key, json);
        editor.commit();
        Log.d("SAVED_FILE", json);
    }

    public Recipe getRecipe(){
        Gson gson = new Gson();
        String recipeData = preferences.getString(RECIPE_CHOSEN, "");
        Recipe recipe = gson.fromJson(recipeData, Recipe.class);
        Log.d("LOADED FILE", recipeData);
        return recipe;
    }

    public void putInt(String key, int value) {
        editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

}
