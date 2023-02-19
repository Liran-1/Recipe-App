package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookbook.fragments.HomeFragment;
import com.example.cookbook.fragments.SettingsFragment;
import com.example.cookbook.fragments.UserFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private MenuItem nav_home;
    private MenuItem nav_settings;
    private MenuItem nav_account;
    private TextView navHeader_TXT_username;
    private ImageView navHeader_IMG_userImg;
    private ExtendedFloatingActionButton main_FAB_addRecipe;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Fragment main_FRG_fragment;
    private Toast toaster;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        findViews();
        initViews();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    private void findViews() {
        toolbar = findViewById(R.id.main_TLBR_toolbar);
        drawerLayout = findViewById(R.id.main_DRWR_loggedIn);
        nav_home = findViewById(R.id.nav_home);
        nav_account = findViewById(R.id.nav_account);
        nav_settings = findViewById(R.id.nav_settings);
        navHeader_TXT_username = findViewById(R.id.navHeader_TXT_username);
        navHeader_IMG_userImg = findViewById(R.id.navHeader_IMG_userImg);
        navigationView = findViewById(R.id.main_NAVVW_NavDrawer);
        main_FAB_addRecipe = findViewById(R.id.main_FAB_addRecipe);
    }

    private void initViews() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        main_FAB_addRecipe.setOnClickListener(view -> addNewRecipe());

        initDrawerMenu();
        setDrawer();

    }

    private void addNewRecipe() {
        Intent intent = new Intent(MainActivity.this, RecipeCreationActivity.class);
        startActivity(intent);
        finish();
    }

    private void setDrawer() {
        String username = user.getDisplayName();
//        if(username != null)
//            navHeader_TXT_username.setText(username);
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
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, new HomeFragment()).commit();
                break;

            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, new UserFragment()).commit();
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, new SettingsFragment()).commit();
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

}