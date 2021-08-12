package dev.mattdev.nvdevaluacion.Model.Rest

import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitRequest {

    @GET("apod")
    suspend fun getApod(@Query("count") count: Int,
                        @Query("api_key") apikey: String): ArrayList<Apod>
}