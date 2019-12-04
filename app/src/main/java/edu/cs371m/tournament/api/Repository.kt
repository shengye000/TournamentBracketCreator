package edu.cs371m.tournament.api

class Repository(private val api : ChallongeApi){

    suspend fun getChallonge(): List<ChallongeInfo> {
        return api.getChallongeResponse()
    }

}