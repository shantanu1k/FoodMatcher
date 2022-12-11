package com.cowday.foodmatcher.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cowday.foodmatcher.data.SimpleBeerItem
import com.cowday.foodmatcher.repository.PunkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: PunkRepository): ViewModel() {
    var welcomeTextVisibility = false
    var searchResultTextVisibility = false
    var searchResultText = ""
    private var _beers: MutableLiveData<List<SimpleBeerItem>> = MutableLiveData()
    val beers: LiveData<List<SimpleBeerItem>>
        get() = _beers
    suspend fun getBeersForFood(foodName: String){
        repository.getBeersForFood(foodName)
    }
    suspend fun getBeersForFoodFromDatabase(foodName: String){
        _beers.value = repository.getBeersForFoodFromDatabase(foodName)
    }
    fun normalizeText(foodName: String): String{
        var normalizedName = ""
        for(i in foodName){
            normalizedName += if(i == ' '){
                '_'
            } else {
                i.lowercase()
            }
        }
        return normalizedName
    }
}