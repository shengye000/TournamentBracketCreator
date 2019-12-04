package edu.cs371m.tournament.api

import com.google.gson.Gson
import retrofit2.Call


class Repository(private val api : ChallongeApi){


//    fun unpackPosts(response: ChallongeApi.ChallongeParticipants) : List<ChallongeInfo> {
//        val list = mutableListOf<ChallongeInfo>()
//        for(i in 0 until (response.participants.size)){
//            list.add(response.participants[i])
//        }
//        return list
//    }
//
//    suspend fun getChallonge() : List<ChallongeInfo>{
//        return unpackPosts(api.getChallongeResponse())
//    }

    suspend fun getChallonge(): List<ChallongeInfo> {
        return api.getChallongeResponse()
    }

}