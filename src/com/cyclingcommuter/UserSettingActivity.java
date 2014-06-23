package com.cyclingcommuter;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class UserSettingActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener, OnPreferenceChangeListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);
		setPreferenceSummary(SettingsConstants.ZIPCODE);
		setPreferenceSummary(SettingsConstants.TO_WORK_TIME);
		setPreferenceSummary(SettingsConstants.TO_HOME_TIME);
	}

	@Override
	protected void onResume() {
		super.onResume();
		findPreference(SettingsConstants.ZIPCODE)
				.setOnPreferenceChangeListener(this);

		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		setPreferenceSummary(key);
	}

	private void setPreferenceSummary(String key) {
		Preference pref = findPreference(key);
		pref.setSummary(getString(key));
	}

	public String getString(String key) {
		SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
		return sp.getString(key, "");
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (SettingsConstants.ZIPCODE.equals(preference.getKey())) {

			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Invalid Input");
			builder.setMessage("Please Enter a 5-digit zip code");
			builder.setPositiveButton(android.R.string.ok, null);
			builder.show();
			return ZipCode.isValid((String) newValue);
		}

		return true;
	}
}