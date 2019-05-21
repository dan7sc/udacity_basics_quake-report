/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.content.Intent
import android.widget.AdapterView
import android.net.Uri


class EarthquakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)

        // Get the list of earthquakes from {@link QueryUtils}
        val earthquakes: ArrayList<Earthquake> = QueryUtils.extractEarthquakes()

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView: ListView = findViewById<View>(R.id.list) as ListView

        // Create a new adapter that takes the list of earthquakes as input
        val adapter = EarthquakeAdapter(this, earthquakes)

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = adapter

        earthquakeListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // Find the current earthquake that was clicked on
            val currentEarthquake: Earthquake? = adapter.getItem(position)

            // Convert the String URL into a URI object (to pass into the Intent constructor)
            val earthquakeUri: Uri = Uri.parse(currentEarthquake!!.getUrl())

            // Create a new intent to view the earthquake URI
            val websiteIntent = Intent(Intent.ACTION_VIEW, earthquakeUri)

            // Send the intent to launch a new activity
            startActivity(websiteIntent)
        }
    }
}
