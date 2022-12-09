package com.cowday.foodmatcher.network

import com.cowday.foodmatcher.data.BeerItem
import retrofit2.Response
import retrofit2.http.GET

interface PunkApi {
    companion object{
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }

    @GET("beers?page=2&per_page=10")
    suspend fun getBeers(): Response<List<BeerItem>>

}