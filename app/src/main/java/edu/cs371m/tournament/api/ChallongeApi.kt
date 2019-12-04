package edu.cs371m.tournament.api

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import android.R.attr.password
import edu.cs371m.tournament.BuildConfig
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import java.io.IOException


interface ChallongeApi{
    @GET("/v1/tournaments/7xpcu8am/participants.json")
    suspend fun getChallongeResponse() : List<ChallongeInfo>

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