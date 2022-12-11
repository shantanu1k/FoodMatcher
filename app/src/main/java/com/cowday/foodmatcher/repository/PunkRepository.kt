package com.cowday.foodmatcher.repository

import android.util.Log
import com.cowday.foodmatcher.data.BeerItem
import com.cowday.foodmatcher.data.SimpleBeerItem
import com.cowday.foodmatcher.database.BeerDatabase
import com.cowday.foodmatcher.network.PunkApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class PunkRepository @Inject constructor(private val punkApi: PunkApi, beerDatabase: BeerDatabase) {

    private val beerDao = beerDatabase.beerDao()

    suspend fun getBeersForFood(foodName: String){
        val response: Response<List<BeerItem>> = try{
            punkApi.getBeersForFood(foodName)
        }  catch (e: HttpException){
            Log.d("Exception", "HTTP")
            return
        } catch (e: IOException){
            Log.d("Exception", "IO")
            return
        }
        if(response.isSuccessful && response.body() != null){
            cacheBeer(response.body()!!, foodName)
        }
    }

    //Caching the results in the local database
    private suspend fun cacheBeer(beerList: List<BeerItem>, foodName: String){
        for(beer in beerList){
            val simpleBear = SimpleBeerItem(beer.id, beer.imageUrl, beer.name, beer.ph, beer.tagline, beer.description, foodName)
            beerDao.addBeer(simpleBear)
        }
    }

    suspend fun getBeersForFoodFromDatabase(foodName: String): List<SimpleBeerItem>{
        return beerDao.getBeerForFood(foodName)
    }
}