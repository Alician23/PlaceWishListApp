package com.example.placewishlistapp

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceRepository {

    private val TAG = "PLACE_REPOSITORY"

    private val baseURL = "https://claraj.pythonanywhere.com/api/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationHeaderInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val placeService = retrofit.create(PlaceService::class.java)

    suspend fun getAllPlace(): List<Place> {
        try {
            val response = placeService.getAllPlaces()

            if ( response.isSuccessful) { // connected, got data in return
                val places = response.body() ?: listOf()
                return places
            }
            else { // connected to server but server sent an error message
                Log.e(TAG, "Error fetching places from API server ${response.errorBody()} ")
                return listOf() // return empty list
            }

        } catch (ex: Exception) { // can't connect to server - network error
            Log.e(TAG, "Error connecting to  API server", ex)
            return listOf()
        }
    }

    suspend fun  addPlace( place: Place): Place ? {
        try {
            val response = placeService.addPlace(place)
            if (response.isSuccessful) {
                Log.d(TAG, "Created new place ${place} ")
                return  response.body()
            }else {
                Log.e(TAG, "Error creating new place ${response.errorBody()} ")
                return  null
            }

        }catch (ex: Exception) { // can't connect to server - network error
            Log.e(TAG, "Error connecting to API server", ex)
            return null
        }
    }
}
