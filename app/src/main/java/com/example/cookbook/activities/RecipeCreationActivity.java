package com.example.cookbook.activities;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cookbook.R;
import com.example.cookbook.adapter.IngredientAdapter;
import com.example.cookbook.callbacks.IngredientAddedCallback;
import com.example.cookbook.callbacks.IngredientRemoveCallback;
import com.example.cookbook.fragments.IngredientFragment;
import com.example.cookbook.models.Ingredient;
import com.example.cookbook.utils.RecipeSP;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RecipeCreationActivity extends AppCompatActivity {


    private IngredientFragment ingredientFragment;
    private Toast toaster;
    private IngredientAdapter ingredientAdapter;
    private ArrayList<Ingredient> ingredients;

    private Uri cam_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_creation);

        ingredients = new ArrayList<>();

        FindViews();
        InitViews();

        ingredientFragment = new IngredientFragment();
        ingredientFragment.setIngredientRemoveCallback(new IngredientRemoveCallback() {
            @Override
            public void ingredientDeleteClicked(Ingredient ingredient, int position) {
                ingredients.remove(position);
                ingredientAdapter.notifyItemRemoved(position);
            }
        });

//        ingredientFragment.setIngredientAddedCallback(this);

        getSupportFragmentManager().beginTransaction().add(R.id.recipeCreation_FRAME_list, ingredientFragment).commit();

    }

    private void FindViews() {

    }

    private void InitViews() {
    }



    private void addIngredient() {
//        String[] Units = getResources().getStringArray(R.array.Amounts);
//
//        if (TextUtils.isEmpty(ingredient_ETXT_ingredientName.getText()) ||
//                TextUtils.isEmpty(ingredient_ETXT_ingredientAmount.getText()) ||
//                ingredient_SPNR_ingredientUnit.getSelectedItem().toString().equals(Units[0])) {
//            return;
//        } else {
//            String name     = ingredient_ETXT_ingredientName.getText().toString();
//            String amount   = ingredient_ETXT_ingredientAmount.getText().toString();
//            String unit     = ingredient_SPNR_ingredientUnit.getSelectedItem().toString();
//
//            Ingredient ingredient = new Ingredient(name, amount, unit);
//            ingredient_ETXT_ingredientName  .getText().clear();
//            ingredient_ETXT_ingredientAmount.getText().clear();
//            ingredient_SPNR_ingredientUnit  .setSelection(0);
//
//            if(ingredients == null){
//                ingredients = new ArrayList<>();
//            }
//            ingredients.add(ingredient);
//            RecipeSP.getInstance().setIngredients(ingredients);
//            ingredientAdapter = new IngredientAdapter(this, ingredients);
//            ingredientAdapter.notifyDataSetChanged();


//        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 101) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            recipeCreation_IMG_picture.setImageBitmap(bitmap);
//        }
//
//    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RecipeCreationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    public void ingredientAddClicked() {
//        String[] Units = getResources().getStringArray(R.array.Amounts);
//
//        if (TextUtils.isEmpty(ingredient_ETXT_ingredientName.getText()) ||
//                TextUtils.isEmpty(ingredient_ETXT_ingredientAmount.getText()) ||
//                ingredient_SPNR_ingredientUnit.getSelectedItem().toString().equals(Units[0])) {
//            return;
//        } else {
//            String name = ingredient_ETXT_ingredientName.getText().toString();
//            String amount = ingredient_ETXT_ingredientAmount.getText().toString();
//            String unit = ingredient_SPNR_ingredientUnit.getSelectedItem().toString();
//
//            Ingredient ingredient = new Ingredient(name, amount, unit);
//            ingredient_ETXT_ingredientName.getText().clear();
//            ingredient_ETXT_ingredientAmount.getText().clear();
//            ingredient_SPNR_ingredientUnit.setSelection(0);
//
//            if (ingredients == null) {
//                ingredients = new ArrayList<>();
//            }
//            ingredients.add(ingredient);
//
//            ingredientFragment.addItem(ingredient);
//
//            RecipeSP.getInstance().setIngredients(ingredients);
////            ingredientAdapter = new IngredientAdapter(this, ingredients);
////            ingredientAdapter.notifyDataSetChanged();
////            ingredientFragment.setIngredientAddedCallback(this);
////            ingredientFragment.addItem(ingredient, ingredients.size());
////            getSupportFragmentManager().beginTransaction().replace(R.id.recipeCreation_FRAME_list, ingredientFragment).commit();
//
////            getSupportFragmentManager().beginTransaction().add(R.id.recipeCreation_FRAME_list, ingredientFragment).commit();
//        }

//    }


}