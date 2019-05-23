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
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

import java.util.ArrayList



class EarthquakeActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<Earthquake>> {

    /** Adapter for the list of earthquakes  */
    private var mAdapter: EarthquakeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView: ListView = findViewById<View>(R.id.list) as ListView

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

        // Get a reference to the LoaderManager, in order to interact with loaders.
        val loaderManager: android.app.LoaderManager? = loaderManager

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager!!.initLoader(EARTHQUAKE_LOADER_ID, null, this)
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<List<Earthquake>> {
        // Create a new loader for the given URL
        return EarthquakeLoader(this, USGS_REQUEST_URL)
    }


    override fun onLoadFinished(loader: Loader<List<Earthquake>>, earthquakes: List<Earthquake>?) {
        // Clear the adapter of previous earthquake data
        mAdapter!!.clear()

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter!!.addAll(earthquakes)
        }
    }

    override fun onLoaderReset(loader: Loader<List<Earthquake>>) {
        // Loader reset, so we can clear out our existing data.
        mAdapter!!.clear()
    }

    companion object {

        private val LOG_TAG = EarthquakeActivity::class.java.name

        /** URL for earthquake data from the USGS dataset  */
        private const val USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10"

        /**
         * Constant value for the earthquake loader ID. We can choose any integer.
         * This really only comes into play if you're using multiple loaders.
         */
        private const val EARTHQUAKE_LOADER_ID = 1
    }
}


///*
// * Copyright (C) 2016 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//@file:Suppress("DEPRECATION")
//
//package com.example.android.quakereport
//
////import android.annotation.SuppressLint
//import android.app.LoaderManager
//import android.content.Loader
//import android.content.Intent
//import android.net.Uri
////import android.os.AsyncTask
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.view.View
//import android.widget.AdapterView
//import android.widget.ListView
//
//import java.util.ArrayList
//
//
//
//abstract class EarthquakeActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<Earthquake>> {
//    /**
//     * Constant value for the earthquake loader ID. We can choose any integer.
//     * This really only comes into play if you're using multiple loaders.
//     */
//    private val EARTHQUAKE_LOADER_ID = 1
//
//    /** Adapter for the list of earthquakes  */
//    private var mAdapter: EarthquakeAdapter? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.earthquake_activity)
//
//        // Find a reference to the {@link ListView} in the layout
//        val earthquakeListView: ListView = findViewById<View>(R.id.list) as ListView
//
//        // Create a new adapter that takes an empty list of earthquakes as input
//        mAdapter = EarthquakeAdapter(this, ArrayList())
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
//        earthquakeListView.adapter = mAdapter
//
//        // Set an item click listener on the ListView, which sends an intent to a web browser
//        // to open a website with more information about the selected earthquake.
//        earthquakeListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            // Find the current earthquake that was clicked on
//            val currentEarthquake: Earthquake? = mAdapter!!.getItem(position)
//
//            // Convert the String URL into a URI object (to pass into the Intent constructor)
//            val earthquakeUri: Uri? = Uri.parse(currentEarthquake!!.getUrl())
//
//            // Create a new intent to view the earthquake URI
//            val websiteIntent = Intent(Intent.ACTION_VIEW, earthquakeUri)
//
//            // Send the intent to launch a new activity
//            startActivity(websiteIntent)
//        }
//
////        // Start the AsyncTask to fetch the earthquake data
////        val task = EarthquakeAsyncTask()
////        task.execute(USGS_REQUEST_URL)
//
//        // Get a reference to the LoaderManager, in order to interact with loaders.
//        val loaderManager: LoaderManager = loaderManager
//
//        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
//        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
//        // because this activity implements the LoaderCallbacks interface).
//        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this)
//    }
////
////    /**
////     * [AsyncTask] to perform the network request on a background thread, and then
////     * update the UI with the list of earthquakes in the response.
////     *
////     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
////     * an output type. Our task will take a String URL, and return an Earthquake. We won't do
////     * progress updates, so the second generic is just Void.
////     *
////     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
////     * The doInBackground() method runs on a background thread, so it can run long-running code
////     * (like network activity), without interfering with the responsiveness of the app.
////     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
////     * UI thread, so it can use the produced data to update the UI.
////     */
////    @SuppressLint("StaticFieldLeak")
////    private inner class EarthquakeAsyncTask : AsyncTask<String, Void, List<Earthquake>>() {
////
////        /**
////         * This method runs on a background thread and performs the network request.
////         * We should not update the UI from a background thread, so we return a list of
////         * [Earthquake]s as the result.
////         */
////        override fun doInBackground(vararg urls: String?): List<Earthquake>? {
////            // Don't perform the request if there are no URLs, or the first URL is null
////            return if (urls.isEmpty() || urls[0] == null) {
////                null
////            } else QueryUtils.fetchEarthquakeData(urls[0]!!)
////
////        }
////
////        /**
////         * This method runs on the main UI thread after the background work has been
////         * completed. This method receives as input, the return value from the doInBackground()
////         * method. First we clear out the adapter, to get rid of earthquake data from a previous
////         * query to USGS. Then we update the adapter with the new list of earthquakes,
////         * which will trigger the ListView to re-populate its list items.
////         */
////        override fun onPostExecute(data: List<Earthquake>?) {
////            // Clear the adapter of previous earthquake data
////            mAdapter!!.clear()
////
////            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
////            // data set. This will trigger the ListView to update.
////            if (data != null && !data.isEmpty()) {
////                mAdapter!!.addAll(data)
////            }
////        }
////    }
//
//    override fun onCreateLoader(i: Int, bundle: Bundle): Loader<List<Earthquake>> {
//        // Create a new loader for the given URL
//        return EarthquakeLoader(this, USGS_REQUEST_URL)
//    }
//
//    override fun onLoadFinished(loader: Loader<List<Earthquake>>, earthquakes: List<Earthquake>?) {
//        // Clear the adapter of previous earthquake data
//        mAdapter!!.clear()
//
//        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//        // data set. This will trigger the ListView to update.
//        if (earthquakes != null && !earthquakes.isEmpty()) {
//            mAdapter!!.addAll(earthquakes)
//        }
//    }
//
//    override fun onLoaderReset(loader: Loader<List<Earthquake>>) {
//        // Loader reset, so we can clear out our existing data.
//        mAdapter!!.clear()
//    }
//
//    companion object {
//        /** URL for earthquake data from the USGS dataset  */
//        private const val USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10"
//    }
//}
