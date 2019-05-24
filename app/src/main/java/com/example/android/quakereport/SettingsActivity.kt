@file:Suppress("DEPRECATION")

package com.example.android.quakereport

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
    }

    class EarthquakePreferenceFragment : PreferenceFragment()
}
