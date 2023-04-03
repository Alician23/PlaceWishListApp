package com.example.placewishlistapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "PLACES_VIEW_FOR_MODEL"

class PlacesViewModel: ViewModel() {

//    private val placeNames = mutableListOf<Place>(Place("Sydney, AU", " To see Kangaroo hopping"),
//        Place("Patagonia, Chile","To see cows", starred = true),
//        Place("Auckland, NZ", "dddd", starred = true))

    fun getPlaces() {
      }

    fun addNewPlace(place: Place){
        //todo
    }

    fun addNewReason(place: Place) {
        if (placeNames.any { placeReason -> placeReason.reason.uppercase() == place.reason.uppercase() }) {
            return -1
        }
        return  if (position == null) {
            placeNames.add(place)
            return placeNames.lastIndex
        } else {
            placeNames.add(position, place)
            return position
        }

    }

//    fun movePlace(from: Int, to: Int) {
//        // Remove place and save value
//        val place = placeNames.removeAt(from)
//        // Insert into list at new position
//        placeNames.add(to, place)
//        Log.d(TAG, place.toString())
//    }

    fun deletePlace(position: Int) {
        //todo
    }

    fun updatePlace(place: Place) {
        // todo
    }
}

