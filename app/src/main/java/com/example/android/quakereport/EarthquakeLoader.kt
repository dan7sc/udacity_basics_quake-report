@file:Suppress("DEPRECATION")

package com.example.android.quakereport


import android.content.AsyncTaskLoader
import android.content.Context
import android.util.Log

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
class EarthquakeLoader
/**
 * Constructs a new [EarthquakeLoader].
 *
 * @param context of the activity
 * @param url to load data from
 */
(context: Context, url: String) : AsyncTaskLoader<List<Earthquake>>(context) {
     /** Query URL  */
     private val mUrl: String? = url

    override fun onStartLoading() {
        Log.i(LOG_TAG, "TEST: onStartLoading() called ...")
        forceLoad()
    }

    /**
     * This is on a background thread.
     */
    override fun loadInBackground(): List<Earthquake>? {
        Log.i(LOG_TAG, "TEST: loadInBackground() called ...")

        return if (mUrl == null) {
            null
        } else QueryUtils.fetchEarthquakeData(mUrl)

        // Perform the network request, parse the response, and extract a list of earthquakes.
    }

    companion object {

        /** Tag for log messages  */
        private val LOG_TAG = EarthquakeLoader::class.java.name
    }
}
