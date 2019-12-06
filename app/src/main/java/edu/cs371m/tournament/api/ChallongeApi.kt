package edu.cs371m.tournament.api

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface ChallongeApi{

    @GET("/v1/tournaments/{url}/participants.json")
    suspend fun getChallongeResponse(@Path("url") url : String,
                                     @Query("api_key") apiKey: String) : List<ChallongeInfo>

    @GET("/v1/tournaments.json")
    suspend fun getTournamentResponse(@Query("api_key") apiKey: String) : List<TournamentInfo>

    companion object {
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("api.challonge.com")
            .build()

        fun create(): ChallongeApi = create(httpurl)

        private fun create(httpURL: HttpUrl): ChallongeApi{
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply{
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .addInterceptor{chain ->
                    val request = chain.request()
                    var response = chain.proceed(request)
                        if (response.code == 401){
                            Log.d("debug", "Wrong API Key Error")
                        }
                    response
                }
                .build()
            return Retrofit.Builder()
                .baseUrl(httpURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChallongeApi::class.java)
        }
    }
}