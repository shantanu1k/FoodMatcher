package com.cowday.foodmatcher.network

import com.cowday.foodmatcher.data.BeerItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkApi {
    companion object{
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }

    @GET("beers?page=1&per_page=10")
    suspend fun getBeers(): Response<List<BeerItem>>

    @GET("beers")
    suspend fun getBeersForFood(@Query("food") foodName: String): Response<List<BeerItem>>
}