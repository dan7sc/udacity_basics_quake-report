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


class EarthquakeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)

        // Create a fake list of earthquake locations.
        val earthquakes = ArrayList<Earthquake>()
        earthquakes.add(Earthquake("7.2", "San Francisco", "Jan 21, 2016"))
        earthquakes.add(Earthquake("6.1", "London", "Feb 2, 2015"))
        earthquakes.add(Earthquake("3.9", "Tokyo", "July 20, 2014"))
        earthquakes.add(Earthquake("5.4", "Mexico City", "Nov 10, 2013"))
        earthquakes.add(Earthquake("2.8", "Moscow", "May 3, 2013"))
        earthquakes.add(Earthquake("4.9", "Rio de Janeiro", "Aug 19, 2012"))
        earthquakes.add(Earthquake("1.6", "Paris", "Oct 10, 2011"))

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView: ListView = findViewById<View>(R.id.list) as ListView

        // Create a new adapter that takes the list of earthquakes as input
        val adapter = EarthquakeAdapter(this, earthquakes)

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = adapter
    }
}
