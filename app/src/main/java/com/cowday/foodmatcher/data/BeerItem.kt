package com.cowday.foodmatcher.data

data class BeerItem(
    val abv: Double?,
    val attenuationLevel: Double?,
    val boilVolume: BoilVolume?,
    val brewersTips: String?,
    val contributedBy: String?,
    val description: String?,
    val ebc: Double?,
    val firstBrewed: String?,
    val foodPairing: List<String>?,
    val ibu: Double?,
    val id: Int?,
    val imageUrl: String?,
    val ingredients: Ingredients?,
    val method: Method?,
    val name: String?,
    val ph: Double?,
    val srm: Double?,
    val tagline: String?,
    val targetFg: Double?,
    val targetOg: Double?,
    val volume: Volume?
)