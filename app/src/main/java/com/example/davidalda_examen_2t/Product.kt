package com.example.davidalda_examen_2t

data class ProductResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val results: List<Product>
)

data class Product(
    val _id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val image: String,
    val active: Boolean
)