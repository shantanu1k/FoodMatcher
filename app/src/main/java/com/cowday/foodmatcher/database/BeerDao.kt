package com.cowday.foodmatcher.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.cowday.foodmatcher.data.SimpleBeerItem

@Dao
interface BeerDao {

    @Insert(onConflict = IGNORE)
    suspend fun addBeer(simpleBeerItem: SimpleBeerItem)

    @Query("Select * from beer_table where food_name = :foodName")
    suspend fun getBeerForFood(foodName: String): List<SimpleBeerItem>

}