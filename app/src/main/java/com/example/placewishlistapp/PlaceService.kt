package com.example.placewishlistapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PlaceService {

    @GET("places/")
    suspend fun getAllPlaces(): Response<List>Place>>

    @POST("places/")
    suspend fun addPlace(@Body place: Place): Response<Place>

    // to update place

    // todo delete place

}