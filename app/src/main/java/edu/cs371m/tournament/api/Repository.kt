package edu.cs371m.tournament.api

class Repository(private val api : ChallongeApi){

    suspend fun getChallonge(url : String): List<ChallongeInfo> {
        return api.getChallongeResponse(url)
    }

    suspend fun getTournament() :List<TournamentInfo> {
        return api.getTournamentResponse()
    }
}