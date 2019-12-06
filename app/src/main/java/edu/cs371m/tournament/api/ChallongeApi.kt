package edu.cs371m.tournament.api

import android.util.Log
import android.widget.Toast
import edu.cs371m.tournament.MainActivity
import edu.cs371m.tournament.Tournament
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException


interface ChallongeApi{

    //Tournament 1: 7xpcu8am
    //TOurnament 2: 70wz88hu
    @GET("/v1/tournaments/{url}/participants.json")
    suspend fun getChallongeResponse(@Path("url") url : String,
                                     @Query("api_key") apiKey: String) : List<ChallongeInfo>

    @GET("/v1/tournaments.json")
    suspend fun getTournamentResponse(@Query("api_key") apiKey: String) : List<TournamentInfo>

    companion object {
        var httpurl = HttpUrl.Builder()
            //before stuff: CapK:R3Xxr5i4HvMOKLIaito1hOtArNGVEEYilmxPj4Ts@
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
                            Log.d("debug","INCORRECT AUTH KEY")
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