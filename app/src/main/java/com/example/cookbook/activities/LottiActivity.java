package com.example.cookbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cookbook.R;

public class LottiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotti);

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_ANIM_lottie);
        lottieAnimationView.resumeAnimation();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }, 5000);


    }


}