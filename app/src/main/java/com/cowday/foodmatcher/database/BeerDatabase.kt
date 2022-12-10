package com.cowday.foodmatcher.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cowday.foodmatcher.data.BeerItem
import com.cowday.foodmatcher.data.SimpleBeerItem
import com.cowday.foodmatcher.utils.StringConverters

@Database(entities = [SimpleBeerItem::class], exportSchema = false, version = 2)
@TypeConverters(StringConverters::class)
abstract class BeerDatabase: RoomDatabase() {
    abstract fun beerDao(): BeerDao
}