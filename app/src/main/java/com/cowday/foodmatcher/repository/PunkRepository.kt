package com.cowday.foodmatcher.repository

import android.util.Log
import com.cowday.foodmatcher.data.Beer
import com.cowday.foodmatcher.data.BeerItem
import com.cowday.foodmatcher.network.PunkApi
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class PunkRepository @Inject constructor(private val punkApi: PunkApi) {
    suspend fun getDataFromNetwork(): List<BeerItem>{
        val response: Response<List<BeerItem>> = try{
            punkApi.getBeers()
        } catch (e: HttpException){
            Log.d("Exception", "HTTP")
            return emptyList()
        } catch (e: IOException){
            Log.d("Exception", "IO")
            return emptyList()
        }
        if(response.isSuccessful && response.body() != null){
            return response.body()!!
        }
        return emptyList()
    }
}