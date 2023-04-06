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

    suspend fun getAllPlace(): ApiResult<List<Place>> {
        try {
            val response = placeService.getAllPlaces()

            if (response.isSuccessful) { // connected, got data in return
                val places = response.body()
                Log.d(TAG, "List of places {$places}")
                return ApiResult(ApiStatus.SUCCESS, response.body(), null)

            } else { // connected to server but server sent an error message
                Log.e(TAG, "Error fetching places from API server ${response.errorBody()} ")
                return ApiResult(ApiStatus.SERVE_ERROR, null, "Error fetching places")
            }

        } catch (ex: Exception) { // can't connect to server - network error
            Log.e(TAG, "Error connecting to  API server", ex)
            return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")

        }
    }

    suspend fun addPlace(place: Place): ApiResult<Place> {
        try {
            val response = placeService.addPlace(place)
            if (response.isSuccessful) {
                Log.d(TAG, "Created new place  ${place} ")
                return ApiResult(ApiStatus.SUCCESS, null, "Place created")
            } else {
                Log.e(TAG, "Error creating new place ${response.errorBody()} ")
                return ApiResult(ApiStatus.SERVE_ERROR, null, "Error adding place - is name unique")
            }

        } catch (ex: Exception) { // can't connect to server - network error
            Log.e(TAG, "Error connecting to API server", ex)
            return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
        }
    }

    suspend fun updatePlace(place: Place) : ApiResult<Place> {

        if (place.id == null) {
            Log.e(TAG, "Error - trying to update place with no ID")
            return ApiResult(ApiStatus.SERVE_ERROR, null, "Error - trying to update place with no ID")

        } else {
            try {
                val response = placeService.updatePlace(place, place.id)
                if (response.isSuccessful) {
                    Log.d(TAG, "Created new place  ${place} ")
                    return ApiResult(ApiStatus.SUCCESS,
                        null, "Place created")
                } else {
                    Log.e(TAG, "Error creating new place ${response.errorBody()} ")
                    return ApiResult(ApiStatus.SERVE_ERROR, null, "Error adding place - is name unique")
                }

            } catch (ex: Exception) { // can't connect to server - network error
                Log.e(TAG, "Error connecting to API server", ex)
                return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
            }
        }

    }

    suspend fun deletePlace(place: Place): ApiResult<Nothing> {

        if (place.id == null) {
            Log.e(TAG, "Error - trying to delete place with no ID")
            return ApiResult(ApiStatus.SERVE_ERROR, null, "Error - trying to delete place with no ID")

        } else {
            try {
                val response = placeService.updatePlace(place, place.id)
                if (response.isSuccessful) {
                    Log.d(TAG, "Created new place  ${place} ")
                    return ApiResult(ApiStatus.SUCCESS,
                        null, "Place deleted")
                } else {
                    Log.e(TAG, "Error creating new place ${response.errorBody()} ")
                    return ApiResult(ApiStatus.SERVE_ERROR, null, "Error deleted place")
                }

            } catch (ex: Exception) { // can't connect to server - network error
                Log.e(TAG, "Error connecting to API server", ex)
                return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
            }
        }
    }

}
