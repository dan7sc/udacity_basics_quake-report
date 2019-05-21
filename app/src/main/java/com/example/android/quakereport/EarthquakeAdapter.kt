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
     * The part of the location string from the USGS service that we use to determine
     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
     */
    private val LOCATION_SEPARATOR: String = " of "

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

        // Get the original location string from the Earthquake object,
        // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
        val originalLocation: String? = currentEarthquake.getLocation()

        // If the original location string (i.e. "5km N of Cairo, Egypt") contains
        // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
        // then store the primary location separately from the location offset in 2 Strings,
        // so they can be displayed in 2 TextViews.
        val primaryLocation: String?
        val locationOffset: String?

        // Check whether the originalLocation string contains the " of " text
        if (originalLocation!!.contains(this.LOCATION_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " of " text. We expect an array of 2 Strings, where
            // the first String will be "5km N" and the second String will be "Cairo, Egypt".
            val parts: List<String> = originalLocation.split(this.LOCATION_SEPARATOR)
            // Location offset should be "5km N " + " of " --> "5km N of"
            locationOffset = parts[0] + this.LOCATION_SEPARATOR
            // Primary location should be "Cairo, Egypt"
            primaryLocation = parts[1]
        } else {
            // Otherwise, there is no " of " text in the originalLocation string.
            // Hence, set the default location offset to say "Near the".
            locationOffset = context.getString(R.string.near_the)
            // The primary location will be the full location string "Pacific-Antarctic Ridge".
            primaryLocation = originalLocation
        }

        // Find the TextView with view ID location
        val primaryLocationView: TextView = listItemView.findViewById<View>(R.id.primary_location) as TextView
        // Display the location of the current earthquake in that TextView
        primaryLocationView.text = primaryLocation

        // Find the TextView with view ID location offset
        val locationOffsetView: TextView = listItemView.findViewById<View>(R.id.location_offset) as TextView
        // Display the location offset of the current earthquake in that TextView
        locationOffsetView.text = locationOffset

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
}

