package com.olehka.datakick.utilities


const val EMPTY = ""
const val COMMA = ","
const val DASH = "-"
const val UNAVAILABLE = "Unavailable"

// Local Repository
const val PRODUCTS_DATABASE_NAME = "products.db"
const val GET_ALL_PRODUCTS_QUERY = "SELECT * FROM Product"
const val GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM Product WHERE id = :id"
const val SEARCH_PRODUCTS_QUERY = "SELECT * FROM Product WHERE name LIKE :query"

// Model
const val KEY_PRODUCT_ID = "gtin14"
const val KEY_PRODUCT_BRAND = "brand_name"
const val KEY_PRODUCT_NAME = "name"
const val KEY_PRODUCT_SIZE = "size"
const val KEY_PRODUCT_IMAGES = "images"
const val KEY_PRODUCT_IMAGE_URL = "url"

// Web service
const val API_BASE_URL = "https://www.datakick.org/api/"
const val PATH_ALL_PRODUCTS = "items"