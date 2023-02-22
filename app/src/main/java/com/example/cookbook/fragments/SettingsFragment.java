package com.example.cookbook.fragments;

import static com.example.cookbook.activities.MainActivity.navigationView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cookbook.R;
import com.example.cookbook.activities.LoginActivity;
import com.example.cookbook.activities.LottiActivity;
import com.example.cookbook.callbacks.OnBackPressedCallback;
import com.example.cookbook.utils.LocaleHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class SettingsFragment extends Fragment implements OnBackPressedCallback {

    private Context context;
    private Resources resources;
    private MaterialTextView settings_LBL_changeLanguage;
    private MaterialButton settings_BTN_hebrew, settings_BTN_english, settings_BTN_russian;

    private CategoryRVFragment categoryRVFragment;                  // for return

    public static OnBackPressedCallback onBackPressedCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        findViews(view);
        initViews();

        return view;
    }

    private void initViews() {
        // setting up on click listener event over the button
        // in order to change the language with the help of
        // LocaleHelper class

        settings_BTN_hebrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = LocaleHelper.getLocale(getActivity()), to = "iw";

                Translator translator = setTranslator(from, to);;
                getTranslations(translator);

                context = LocaleHelper.setLocale(getActivity(), "iw");
                resources = context.getResources();
//                settings_LBL_changeLanguage.setText(resources.getString(R.string.change_language));
            }
        });

        settings_BTN_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = LocaleHelper.getLocale(getActivity()), to = "en";

                Translator translator = setTranslator(from, to);;
                getTranslations(translator);
                context = LocaleHelper.setLocale(getActivity(), "en");
                resources = context.getResources();
//                settings_LBL_changeLanguage.setText(resources.getString(R.string.change_language));
//                refreshUI();
            }
        });

        settings_BTN_russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = LocaleHelper.getLocale(getActivity()), to = "ru";

                Translator translator = setTranslator(from, to);
                getTranslations(translator);

                context = LocaleHelper.setLocale(getActivity(), "ru");
                resources = context.getResources();
//                settings_LBL_changeLanguage.setText(resources.getString(R.string.change_language));
            }
        });
    }

    private void getTranslations(Translator translator) {
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                            categoryRVFragment.setTranslator(translator);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                            }
                        });

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getActivity(), LottiActivity.class);
            startActivity(intent);
            getActivity().finish();
        }, 3000);

    }

    private Translator setTranslator(String from, String to) {
        String languageSource;
        String languageTarget;
        Log.d("Languages", from + " " + to);
        if (from.equals("iw"))
            languageSource = TranslateLanguage.HEBREW;
        else if (from.equals("ru"))
            languageSource = TranslateLanguage.RUSSIAN;
        else
            languageSource = TranslateLanguage.ENGLISH;

        if (to.equals("iw"))
            languageTarget = TranslateLanguage.HEBREW;
        else if (to.equals("ru"))
            languageTarget = TranslateLanguage.RUSSIAN;
        else
            languageTarget = TranslateLanguage.ENGLISH;


        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(languageSource)
                        .setTargetLanguage(languageTarget)
                        .build();
        final Translator sourceToTargetTranslator =
                Translation.getClient(options);
        return sourceToTargetTranslator;
}

    private void refreshUI() {
        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.main_FRG_fragment, this).commit();
    }

    private void findViews(View view) {
        settings_BTN_hebrew = view.findViewById(R.id.settings_BTN_hebrew);
        settings_BTN_english = view.findViewById(R.id.settings_BTN_english);
        settings_BTN_russian = view.findViewById(R.id.settings_BTN_russian);
        settings_LBL_changeLanguage = view.findViewById(R.id.settings_LBL_changeLanguage);
    }


    public void setCategoryRVFragment(CategoryRVFragment categoryRVFragment) {
        this.categoryRVFragment = categoryRVFragment;
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, categoryRVFragment).commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onResume() {
        super.onResume();
        onBackPressedCallback = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        onBackPressedCallback = null;
    }

}