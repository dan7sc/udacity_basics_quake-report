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
@file:Suppress("DEPRECATION")

package com.example.android.quakereport

import android.app.LoaderManager
import android.content.Intent
import android.content.Loader
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

import java.util.ArrayList
import android.widget.TextView
import android.content.Context
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.view.Menu
import android.view.MenuItem
import android.preference.PreferenceManager
import android.content.SharedPreferences



class EarthquakeActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<Earthquake>> {

    /** Adapter for the list of earthquakes  */
    private var mAdapter: EarthquakeAdapter? = null

    /** TextView that is displayed when the list is empty  */
    private var mEmptyStateTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: Earthquake Activity onCreate() called")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView: ListView = findViewById<View>(R.id.list) as ListView

        mEmptyStateTextView = findViewById<View>(R.id.empty_view) as TextView
        earthquakeListView.emptyView = mEmptyStateTextView

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = EarthquakeAdapter(this, ArrayList())

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = mAdapter

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // Find the current earthquake that was clicked on
            val currentEarthquake: Earthquake? = mAdapter!!.getItem(position)

            // Convert the String URL into a URI object (to pass into the Intent constructor)
            val earthquakeUri: Uri = Uri.parse(currentEarthquake!!.getUrl())

            // Create a new intent to view the earthquake URI
            val websiteIntent = Intent(Intent.ACTION_VIEW, earthquakeUri)

            // Send the intent to launch a new activity
            startActivity(websiteIntent)
        }

        // Get a reference to the ConnectivityManager to check state of network connectivity
        val connMgr: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Get details on the currently active default data network
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            val loaderManager: LoaderManager? = loaderManager

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager!!.initLoader(EARTHQUAKE_LOADER_ID, null, this)
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            val loadingIndicator: View = findViewById<View>(R.id.loading_indicator)
            loadingIndicator.visibility = View.GONE

            // Update empty state with no connection error message
            mEmptyStateTextView!!.setText(R.string.no_internet_connection)
        }
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<List<Earthquake>> {
        // Create a new loader for the given URL
        Log.i(LOG_TAG, "TEST: onCreateLoader() called ...")

        val sharedPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val minMagnitude: String? = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default))

        val orderBy: String? = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        )

        val baseUri: Uri = Uri.parse(USGS_REQUEST_URL)
        val uriBuilder: Uri.Builder = baseUri.buildUpon()

        uriBuilder.appendQueryParameter("format", "geojson")
        uriBuilder.appendQueryParameter("limit", "10")
        uriBuilder.appendQueryParameter("minmag", minMagnitude)
        uriBuilder.appendQueryParameter("orderby", orderBy)

        return EarthquakeLoader(this, uriBuilder.toString())
    }

    override fun onLoadFinished(loader: Loader<List<Earthquake>>, earthquakes: List<Earthquake>?) {
        Log.i(LOG_TAG, "TEST: onLoadFinished() called ...")

        // Hide loading indicator because the data has been loaded
        val loadingIndicator: View = findViewById<View>(R.id.loading_indicator)
        loadingIndicator.visibility = View.GONE

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView!!.setText(R.string.no_earthquakes)

        // Clear the adapter of previous earthquake data
        mAdapter!!.clear()

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter!!.addAll(earthquakes)
        }
    }

    override fun onLoaderReset(loader: Loader<List<Earthquake>>) {
        Log.i(LOG_TAG, "TEST: onLoaderReset() called ...")

        // Loader reset, so we can clear out our existing data.
        mAdapter!!.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_settings) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val LOG_TAG = EarthquakeActivity::class.java.name

        /** URL for earthquake data from the USGS dataset  */
        private const val USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query"

        /**
         * Constant value for the earthquake loader ID. We can choose any integer.
         * This really only comes into play if you're using multiple loaders.
         */
        private const val EARTHQUAKE_LOADER_ID = 1
    }
}
