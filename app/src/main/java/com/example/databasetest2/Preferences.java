package com.example.databasetest2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class Preferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        loadSettings();
    }

    private void loadSettings() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean nightModeCheckBox = sp.getBoolean("NIGHT", false);
        if (nightModeCheckBox) {
            getListView().setBackgroundColor(Color.parseColor("#808080"));
        } else {
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }

        CheckBoxPreference nightModeCheckBox_instance = (CheckBoxPreference) findPreference("NIGHT");
        nightModeCheckBox_instance.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preferences, Object obj) {

                boolean yes = (boolean) obj;

                if (yes) {
                    getListView().setBackgroundColor(Color.parseColor("#808080"));
                } else {
                    getListView().setBackgroundColor(Color.parseColor("#ffffff"));
                }
                return true;
            }
        });

        ListPreference lP = (ListPreference) findPreference("ORIENTATION");

        String orientation = sp.getString("ORIENTATION", "false");
        if ("1".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            lP.setSummary(lP.getEntry());
        } else if ("2".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            lP.setSummary(lP.getEntry());
        } else if ("3".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            lP.setSummary(lP.getEntry());
        }

        lP.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preferences, Object obj) {

                String items = (String) obj;
                if (preferences.getKey().equals("ORIENTATION")) {
                    switch (items) {
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }

                    ListPreference lPP = (ListPreference) preferences;
                    lPP.setSummary(lPP.getEntries()[lPP.findIndexOfValue(items)]);

                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}
