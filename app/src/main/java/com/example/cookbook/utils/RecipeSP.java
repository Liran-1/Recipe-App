package com.example.cookbook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cookbook.models.Ingredient;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecipeSP {

    private static final String DB_FILE = "DB_FILE";
    private static final String RECIPE_RECORDS = "RECIPE_RECORDS";

    private final int HIGH_SCORE_LIST_SIZE = 10;

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

    public ArrayList<Ingredient> getIngredients() {
        TypeToken<List<Ingredient>> listType = new TypeToken<List<Ingredient>>() {};
        Gson gson = new Gson();
        String recipeData = preferences.getString(RECIPE_RECORDS, "");
        ArrayList<Ingredient> Ingredients = gson.fromJson(recipeData, listType.getType());
        Log.d("LOADED FILE", recipeData);
        return Ingredients != null ? Ingredients : new ArrayList<>();
    }

    public void setIngredients(ArrayList<Ingredient> currentIngredients) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Gson gson = new Gson();
        for (Ingredient ingredient : currentIngredients)
            ingredients.add(ingredient);
        String json = gson.toJson(ingredients);
        editor = preferences.edit();
        editor.putString(RECIPE_RECORDS, json);
        editor.commit();
        Log.d("SAVED_FILE", json);

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