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

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat


/**
 * An [EarthquakeAdapter] knows how to create a list item layout for each earthquake
 * in the data source (a list of [Earthquake] objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
class EarthquakeAdapter
/**
 * Constructs a new [EarthquakeAdapter].
 *
 * @param context of the app
 * @param earthquakes is the list of earthquakes, which is the data source of the adapter
 */
(context: Context, earthquakes: ArrayList<Earthquake>) : ArrayAdapter<Earthquake>(context, 0, earthquakes) {

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                    R.layout.earthquake_list_item, parent, false)
        }

        // Find the earthquake at the given position in the list of earthquakes
        val currentEarthquake = getItem(position)

        // Find the TextView with view ID magnitude
        val magnitudeView: TextView = listItemView!!.findViewById<View>(R.id.magnitude) as TextView
        // Display the magnitude of the current earthquake in that TextView
        magnitudeView.text = currentEarthquake!!.getMagnitude()

        // Find the TextView with view ID location
        val locationView: TextView = listItemView.findViewById<View>(R.id.location) as TextView
        // Display the location of the current earthquake in that TextView
        locationView.text = currentEarthquake.getLocation()


        // Create a new Date object from the time in milliseconds of the earthquake
        val dateObject = Date(currentEarthquake.getTimeInMilliseconds())

        // Find the TextView with view ID date
        val dateView: TextView = listItemView.findViewById<View>(R.id.date) as TextView
        // Format the date string (i.e. "Mar 3, 1984")
        val formattedDate = formatDate(dateObject)
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate)

        // Find the TextView with view ID time
        val timeView = listItemView.findViewById(R.id.time) as TextView
        // Format the time string (i.e. "4:30PM")
        val formattedTime = formatTime(dateObject)
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime)

        // Return the list item view that is now showing the appropriate data
        return listItemView
    }
}

/**
 * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
 */
private fun formatDate(dateObject: Date): String {
    val dateFormat = SimpleDateFormat("LLL dd, yyyy", Locale.US)
    return dateFormat.format(dateObject)
}

/**
 * Return the formatted date string (i.e. "4:30 PM") from a Date object.
 */
private fun formatTime(dateObject: Date): String {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.US)
    return timeFormat.format(dateObject)
}
