package com.example.cookbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookbook.R;
import com.example.cookbook.callbacks.CategoryCallback;
import com.example.cookbook.callbacks.RecipeCallback;
import com.example.cookbook.fragments.CreateRecipeFragment;
import com.example.cookbook.fragments.CategoryRVFragment;
import com.example.cookbook.fragments.RecipeFragment;
import com.example.cookbook.fragments.RecipeRVFragment;
import com.example.cookbook.fragments.SettingsFragment;
import com.example.cookbook.fragments.UserFragment;
import com.example.cookbook.models.Category;
import com.example.cookbook.models.Recipe;
import com.example.cookbook.models.User;
import com.example.cookbook.utils.RecipeSP;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private MaterialTextView navHeader_TXT_username;
    private ImageView navHeader_IMG_userImg;
    private Toolbar toolbar;
    public static NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Fragment main_FRG_fragment;
    private CategoryRVFragment categoryRVFragment;
    private CreateRecipeFragment createRecipeFragment;
    private RecipeRVFragment recipeRVFragment;
    private RecipeFragment recipeFragment;
    private UserFragment userFragment;
    private SettingsFragment settingsFragment;

    private Toast toaster;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbRef;
    private User user;
    private ArrayList<Category> categories;
    private int backPressedCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail());
        mDatabase = FirebaseDatabase.getInstance();
        dbRef = mDatabase.getReference();

        Log.d("User", String.valueOf(user));
        Log.d("User", user.getName());

        findViews();
        initViews();

        checkPermissions();

        if (savedInstanceState == null) {
            setFragments();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, categoryRVFragment).commit();

            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    private void setFragments() {
        categoryRVFragment      = new CategoryRVFragment();
        createRecipeFragment    = new CreateRecipeFragment();
        recipeRVFragment        = new RecipeRVFragment();
        userFragment            = new UserFragment();
        settingsFragment        = new SettingsFragment();
        recipeFragment          = new RecipeFragment();

        categoryRVFragment.setCallback(new CategoryCallback() {
            @Override
            public void categoryClicked(Category category, int position) {
                backPressedCounter = 0;
                recipeRVFragment.setCategoryRVFragment(categoryRVFragment);
//                        recipeFragment.chosenCategory = category.getName();
                RecipeSP.getInstance().putString(RecipeSP.CATEGORY_CHOSEN, category.getName());
                RecipeSP.getInstance().putInt(RecipeSP.CATEGORY_CHOSEN_NUM, position + 1);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_FRG_fragment, recipeRVFragment).commit();
            }
        });

        recipeRVFragment.setCallback(new RecipeCallback() {
            @Override
            public void recipeClicked(Recipe recipe, int position) {
                recipeFragment.setRecipeRVFragment(recipeRVFragment);
                RecipeSP.getInstance().putRecipe(RecipeSP.RECIPE_CHOSEN, recipe);
//                    RecipeSP.getInstance().putInt(RecipeSP.RECIPE_CHOSEN_NUM, position);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_FRG_fragment, recipeFragment).commit();
            }
        });


    }

    private void findViews() {
        toolbar = findViewById(R.id.main_TLBR_toolbar);
        drawerLayout = findViewById(R.id.main_DRWR_loggedIn);
        navigationView = findViewById(R.id.main_NAVVW_NavDrawer);

        View headerView = navigationView.getHeaderView(0);
        navHeader_TXT_username = headerView.findViewById(R.id.navHeader_TXT_username);
        navHeader_IMG_userImg = headerView.findViewById(R.id.navHeader_IMG_userImg);

    }

    private void initViews() {
        initDrawerMenu();
        setDrawer();
    }

    private void setDrawer() {
        String name = user.getName();
        if(name != null)
            navHeader_TXT_username.setText(name);
    }

    private void initDrawerMenu() {
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    ////////////////////////DRAWER FUNCTIONS////////////////////////
    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        backPressedCounter = 0;
        switch (item.getItemId()) {
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, categoryRVFragment).commit();
                break;

            case R.id.nav_addRecipe:
                createRecipeFragment.setCategoryRVFragment(categoryRVFragment);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, createRecipeFragment).commit();
                break;

            case R.id.nav_account:
                userFragment.setCategoryRVFragment(categoryRVFragment);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, userFragment).commit();
                break;

            case R.id.nav_settings:
                settingsFragment.setCategoryRVFragment(categoryRVFragment);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, settingsFragment).commit();
                break;

            case R.id.nav_logout:
                toaster = Toast
                        .makeText(this, "Logging out", Toast.LENGTH_SHORT);
                toaster.show();

                LogOut();

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    ////////////////////////END DRAWER FUNCTIONS////////////////////////


    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 101);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (CreateRecipeFragment.onBackPressedCallback != null) {
            CreateRecipeFragment.onBackPressedCallback.onBackPressed();
        }
        else if (RecipeRVFragment.onBackPressedCallback != null) {
            RecipeRVFragment.onBackPressedCallback.onBackPressed();
        }
        else if (RecipeFragment.onBackPressedCallback != null) {
            RecipeFragment.onBackPressedCallback.onBackPressed();
        }
        else if (UserFragment.onBackPressedCallback != null) {
            UserFragment.onBackPressedCallback.onBackPressed();
        }
        else if (SettingsFragment.onBackPressedCallback != null) {
            SettingsFragment.onBackPressedCallback.onBackPressed();
        }
        else {
            backPressedCounter++;
            if(backPressedCounter == 2) {
                super.onBackPressed();
            }
        }
    }

    private void LogOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        toaster = Toast.makeText(MainActivity.this,
                                "Logged out", Toast.LENGTH_SHORT);
                        toaster.show();
                    }
                });
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}