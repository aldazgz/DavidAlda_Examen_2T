package com.example.davidalda_examen_2t

import retrofit2.Response
import retrofit2.http.GET

interface ProductAPI {
    @GET("api/products")
    suspend fun getProducts(): ProductResponse
}