package com.example.cookbook.fragments;

import static com.example.cookbook.activities.MainActivity.navigationView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookbook.R;
import com.example.cookbook.activities.MainActivity;
import com.example.cookbook.callbacks.OnBackPressedCallback;
import com.example.cookbook.utils.LocaleHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

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
                context = LocaleHelper.setLocale(getActivity(), "iw");
                resources = context.getResources();
                settings_LBL_changeLanguage.setText(resources.getString(R.string.change_language));
            }
        });

        settings_BTN_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(getActivity(), "en");
                resources = context.getResources();
                settings_LBL_changeLanguage.setText(resources.getString(R.string.change_language));
            }
        });

        settings_BTN_russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(getActivity(), "ru");
                resources = context.getResources();
                settings_LBL_changeLanguage.setText(resources.getString(R.string.change_language));
            }
        });
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