package com.hinodesoftworks.kanagt;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.hinodesoftworks.kanagt.dialogs.ClearDatabasePrefDialog;

public class PrefsFragment extends PreferenceFragment implements
                                    ClearDatabasePrefDialog.ClearDatabaseDialogListener {

    private PreferenceListener mListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        ClearDatabasePrefDialog cdbPref = (ClearDatabasePrefDialog)
                this.getPreferenceManager().findPreference("clearDatabase");

        cdbPref.setListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (PreferenceListener)activity;

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();

        if (key.matches("showLicenses")){
            mListener.showLicense();
        }

        return true;
    }

    @Override
    public void onPositiveResult() {
        mListener.onDatabaseDeleteClicked();
    }


    public interface PreferenceListener{
        void onDatabaseDeleteClicked();
        void showLicense();
    }



}
