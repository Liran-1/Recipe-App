package com.example.cookbook.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Category {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference();

    private String name, image, menuId;
    private ArrayList<Recipe> recipes;

    public Category() {
    }

    public Category(String name, String image, String menuId) {
        this.name = name;
        this.image = image;
        this.menuId = menuId;
//        this.recipes = getRecipes();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

//    public ArrayList<Recipe> getRecipes() {
//        if (recipes == null)
//            recipes = new ArrayList<>();
//        dbRef.child("Category").child(menuId).get().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                Log.e("firebase", "Error getting data", task.getException());
//            } else {
//                //                    categories = (ArrayList) task.getResult().getValue();
//                Log.d("firebase", String.valueOf(task.getResult().getKey()));
//                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
//
//                    String name = String.valueOf(dataSnapshot.child("Name").getValue());
//                    String description = String.valueOf(dataSnapshot.child("Description").getValue());
//                    String image = String.valueOf(dataSnapshot.child("Image").getValue());
//                    String instructions = String.valueOf(dataSnapshot.child("Instructions").getValue());
////                    String categoryId = String.valueOf(dataSnapshot.child("CategoryId").getValue());
//                    GenericTypeIndicator<Map<String, Ingredient>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Ingredient>>() {};
//                    Map<String,Ingredient> ingredientsMap = dataSnapshot.child("Ingredients")
//                            .getValue(genericTypeIndicator);
//
//                    ArrayList<Ingredient> ingredients = new ArrayList<>();
//                    if(ingredientsMap != null)
////                        ingredients = ingredientsMap.values();
//                        ingredients = new ArrayList<>(ingredientsMap.values());
////                    int likes = (int) dataSnapshot.child("Image").getValue();
//                    int likes = 0;
//                    //                        String menuId = String.valueOf(dataSnapshot.getKey());
//                    Log.d("name", name);
//                    Log.d("image", image);
//                    Recipe recipe = new Recipe(name, description, image, instructions, likes, ingredients);
//                    recipes.add(recipe);
//
////                    Log.d("counted", String.valueOf(categories.size()));
//                }
//            }
//        });
//
//        Log.d("RECIPES", String.valueOf(recipes));
//        return recipes;
//
//    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", menuId='" + menuId + '\'' +
                '}';
    }
}
