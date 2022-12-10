package com.cowday.foodmatcher.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer_table")
data class SimpleBeerItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "ph")
    val ph: Double?,
    @ColumnInfo(name = "tagline")
    val tagline: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "food_name")
    val foodName: String?
)