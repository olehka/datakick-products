/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.utilities

import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.getDeviceWidthInDP(): Int {
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return (metrics.widthPixels / metrics.density).toInt()
}

fun List<Any>?.isNullOrEmpty() = this == null || this.isEmpty()