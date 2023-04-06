package com.example.placewishlistapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

const val TAG = "PLACES_VIEW_FOR_MODEL"

class PlacesViewModel: ViewModel() {

//    private val placeNames = mutableListOf<Place>(Place("Sydney, AU", " To see Kangaroo hopping"),
//        Place("Patagonia, Chile","To see cows", starred = true),
//        Place("Auckland, NZ", "dddd", starred = true))

    private val placeRepository = PlaceRepository()

    val allPlaces = MutableLiveData<List<Place>>(listOf<Place>())
    val userMessage = MutableLiveData<String>()
    init {
        getPlaces()

    }

    fun getPlaces() {
        viewModelScope.launch {
            val apiResult = placeRepository.getAllPlace()
            val userMessage = MutableLiveData<String>(null)
            if(apiResult.status == ApiStatus.SUCCESS) {
                allPlaces.postValue(apiResult.data)
            }else {
                userMessage.postValue(apiResult.message)

            }
        }
      }

    fun addNewPlace(place: Place){
        viewModelScope.launch {
            val apiResult = placeRepository.addPlace(place)
            updateUI(apiResult)
        }
    }

    fun updatePlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placeRepository.addPlace(place)
            updateUI(apiResult)
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placeRepository.addPlace(place)
            updateUI(apiResult)
        }
    }

    private fun updateUI(result: ApiResult<Any>) {
        if (result.status == ApiStatus.SUCCESS) {
            getPlaces() // update UI
        }
        userMessage.postValue(result.message)
    }

}

