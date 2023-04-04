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

    val allPlaces = MutableLiveData<List>Place>>(listOf<Place>())

    init {
        getPlaces()

    }

    fun getPlaces() {
        viewModelScope.launch {
            val places = placeRepository.getAllPlace()
            allPlaces.postValue(places)
        }
      }

    fun addNewPlace(place: Place){
        viewModelScope.launch {
            val newPlace = placeRepository.addPlace(place)
            getPlaces() // update UI
        }
    }

    fun updatePlace(place: Place) {
        viewModelScope.launch {
            placeRepository.updatePlace(place)
            getPlaces() // update UI
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            placeRepository.deletePlace(place)
            getPlaces() // update UI
        }
    }

}

