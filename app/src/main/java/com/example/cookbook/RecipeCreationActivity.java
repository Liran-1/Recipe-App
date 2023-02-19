package com.example.cookbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cookbook.adapter.IngredientAdapter;
import com.example.cookbook.callbacks.IngredientRemoveCallback;
import com.example.cookbook.fragments.IngredientFragment;
import com.example.cookbook.models.Ingredient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RecipeCreationActivity extends AppCompatActivity {

    private TextInputEditText recipeCreation_ETXT_description, ingredient_ETXT_ingredientName,
            ingredient_ETXT_ingredientAmount, recipeCreation_ETXT_instructions;
    private Spinner ingredient_SPNR_ingredientUnit;
    private MaterialButton ingredient_BTN_add;
    private ImageView recipeCreation_IMG_picture;
    private IngredientFragment ingredientFragment;
    private Toast toaster;
    private IngredientAdapter ingredientAdapter;

    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_creation);

        FindViews();
        InitViews();

        checkPermissions();

        ingredients = new ArrayList<>();
        ingredientFragment = new IngredientFragment();
        ingredientFragment.setCallback(new IngredientRemoveCallback() {
            @Override
            public void ingredientDeleteClicked(Ingredient ingredient, int position) {
                ingredients.remove(position);
                ingredientAdapter.notifyItemRemoved(position);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipeCreation_FRAME_list, ingredientFragment).commit();
    }

    private void FindViews() {
        recipeCreation_ETXT_description = findViewById(R.id.recipeCreation_ETXT_description);
        ingredient_ETXT_ingredientName = findViewById(R.id.ingredient_ETXT_ingredientName);
        ingredient_ETXT_ingredientAmount = findViewById(R.id.ingredient_ETXT_ingredientAmount);
        ingredient_SPNR_ingredientUnit = findViewById(R.id.ingredient_SPNR_ingredientUnit);
        ingredient_BTN_add = findViewById(R.id.ingredient_BTN_add);
        recipeCreation_IMG_picture = findViewById(R.id.recipeCreation_IMG_picture);
        recipeCreation_ETXT_instructions = findViewById(R.id.recipeCreation_ETXT_instructions);
    }

    private void InitViews() {
        ingredient_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredient();
            }
        });

        recipeCreation_IMG_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    String msg = "Did not find an app to support this action";
                    toaster = Toast
                            .makeText(RecipeCreationActivity.this, msg, Toast.LENGTH_SHORT);
                    toaster.show();
                }
            }
        });
    }

    private void addIngredient() {
        if (TextUtils.isEmpty(ingredient_ETXT_ingredientName.getText()) ||
                TextUtils.isEmpty(ingredient_ETXT_ingredientAmount.getText()) ||
                ingredient_SPNR_ingredientUnit.getSelectedItem().toString().equals("Unit")) {
            return;
        } else {
            String name = ingredient_ETXT_ingredientName.getText().toString();
            String amount = ingredient_ETXT_ingredientAmount.getText().toString();
            String unit = ingredient_SPNR_ingredientUnit.getSelectedItem().toString();

            Ingredient ingredient = new Ingredient(name, amount, unit);
            if(ingredients.size() == 0){
                ingredients = new ArrayList<>();
            }
            ingredients.add(ingredient);
            ingredientAdapter = new IngredientAdapter(this, ingredients);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            recipeCreation_IMG_picture.setImageBitmap(bitmap);
        }

    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 101);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RecipeCreationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}