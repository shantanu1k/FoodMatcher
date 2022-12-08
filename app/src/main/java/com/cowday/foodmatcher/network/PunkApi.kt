package com.cowday.foodmatcher.network

import com.cowday.foodmatcher.data.BeerItem
import retrofit2.http.GET

interface PunkApi {
    companion object{
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }

    @GET
    fun getBeers(): List<BeerItem>

}