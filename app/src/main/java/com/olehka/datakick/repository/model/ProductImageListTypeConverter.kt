/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.repository.model

import androidx.room.TypeConverter
import com.olehka.datakick.utilities.COMMA
import com.olehka.datakick.utilities.EMPTY

class ProductImageListTypeConverter {

    @TypeConverter
    fun fromListToString(list: List<ProductImage>): String {
        var result = EMPTY
        list.forEach { result += "${it.url}$COMMA" }
        return result.substringBeforeLast(COMMA)
    }

    @TypeConverter
    fun fromStringToList(string: String): List<ProductImage> {
        val result = mutableListOf<ProductImage>()

        if (!string.isBlank()) {
            string.split(COMMA).forEach { result += ProductImage(it) }
        }

        return result
    }
}