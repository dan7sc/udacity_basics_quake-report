@file:Suppress("DEPRECATION")

package com.example.android.quakereport

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.preference.Preference



class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
    }

    class EarthquakePreferenceFragment : PreferenceFragment(), Preference.OnPreferenceChangeListener {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.settings_main)

            val minMagnitude: Preference = findPreference(getString(R.string.settings_min_magnitude_key))
            bindPreferenceSummaryToValue(minMagnitude)
        }

        override fun onPreferenceChange(preference: Preference, value: Any?): Boolean {
            val stringValue: String = value!!.toString()
            preference.summary = stringValue
            return true
        }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = this
            val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.context)
            val preferenceString: String? = preferences.getString(preference.key, "")
            onPreferenceChange(preference, preferenceString)
        }
    }

}
