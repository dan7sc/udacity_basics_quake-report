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

/**
 * An [Earthquake] object contains information related to a single earthquake.
 */
class Earthquake
/**
 * Constructs a new [Earthquake] object.
 *
 * @param magnitude is the magnitude (size) of the earthquake
 * @param location is the city location of the earthquake
 * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
 *  earthquake happened
 * @param url is the website URL to find more details about the earthquake
 */
(magnitude: Double, location: String, timeInMilliseconds: Long, url: String) {

    /** Magnitude of the earthquake  */
    private var mMagnitude: Double = magnitude

    /** Location of the earthquake  */
    private var mLocation: String = location

    /** Date of the earthquake  */
    private var mTimeInMilliseconds: Long = timeInMilliseconds

    /** Website URL of the earthquake  */
    private val mUrl: String? = url

    /**
     * Returns the magnitude of the earthquake.
     */
    fun getMagnitude(): Double {
        return mMagnitude
    }

    /**
     * Returns the location of the earthquake.
     */
    fun getLocation(): String? {
        return mLocation
    }

    /**
     * Returns the time of the earthquake.
     */
    fun getTimeInMilliseconds(): Long {
        return mTimeInMilliseconds
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    fun getUrl(): String? {
        return mUrl
    }
}
