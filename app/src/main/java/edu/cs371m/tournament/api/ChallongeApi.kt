package edu.cs371m.tournament.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.*
import retrofit2.http.Path


interface ChallongeApi{

    //Tournament 1: 7xpcu8am
    //TOurnament 2: 70wz88hu
    @GET("/v1/tournaments/{url}/participants.json")
    suspend fun getChallongeResponse(@Path("url") url : String) : List<ChallongeInfo>

    @GET("/v1/tournaments.json")
    suspend fun getTournamentResponse() : List<TournamentInfo>

    companion object {
        var httpurl = HttpUrl.Builder()
            //before stuff: CapK:R3Xxr5i4HvMOKLIaito1hOtArNGVEEYilmxPj4Ts@
            .scheme("https")
            .username("CapK")
            .password("R3Xxr5i4HvMOKLIaito1hOtArNGVEEYilmxPj4Ts")
            .host("api.challonge.com")
            .build()

        private class AuthenticationInterceptor(user: String, password: String) : Interceptor{
            private val credentials: String = Credentials.basic(user, password)

            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val request = chain.request()
                val authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build()
                return chain.proceed(authenticatedRequest)
            }
        }

        fun create(): ChallongeApi = create(httpurl)

        private fun create(httpURL: HttpUrl): ChallongeApi{
            val client = OkHttpClient.Builder()
//                .addInterceptor(HttpLoggingInterceptor().apply{
//                    this.level = HttpLoggingInterceptor.Level.BASIC
//                })
                .addInterceptor(AuthenticationInterceptor("CapK","R3Xxr5i4HvMOKLIaito1hOtArNGVEEYilmxPj4Ts" ))
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