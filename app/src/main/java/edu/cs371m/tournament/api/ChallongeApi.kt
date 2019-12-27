package edu.cs371m.tournament.api

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface ChallongeApi{

    @GET("/v1/tournaments/{url}/participants.json")
    suspend fun getChallongeResponse(@Path("url") url : String,
                                     @Query("api_key") apiKey: String) : List<ChallongeInfo>

    @GET("/v1/tournaments.json")
    suspend fun getTournamentResponse(@Query("api_key") apiKey: String) : List<TournamentInfo>

    @POST( "v1/tournaments.json")
    suspend fun createTournamentResponse(@Body info : CreateInfo, @Query("api_key") apiKey: String) : TournamentInfo

    @DELETE ("v1/tournaments/{tournament}.json")
    suspend fun deleteTournamentResponse(@Path("tournament") tournament: String,
                                         @Query("api_key") apiKey: String)

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
                        if (response.code == 422){
                            Log.d("debug_422", "Error Message " + response.body!!.string())
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