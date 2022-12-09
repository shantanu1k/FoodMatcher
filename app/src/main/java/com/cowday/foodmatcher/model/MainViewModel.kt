package com.cowday.foodmatcher.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cowday.foodmatcher.data.BeerItem
import com.cowday.foodmatcher.repository.PunkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: PunkRepository): ViewModel() {
    private val _beers: MutableLiveData<List<BeerItem>> = MutableLiveData()
    val beers: LiveData<List<BeerItem>>
        get() = _beers
    suspend fun getDataFromNetwork(){
        _beers.value = repository.getDataFromNetwork()
    }
    suspend fun getBeersForFood(foodName: String){
        _beers.value = repository.getBeersForFood(foodName)
    }
}