package edu.cs371m.tournament.api

class Repository(private val api : ChallongeApi){

    suspend fun getChallonge(url : String, apiKey : String): List<ChallongeInfo> {
        return api.getChallongeResponse(url, apiKey)
    }

    suspend fun getTournament(apiKey: String) :List<TournamentInfo> {
        return api.getTournamentResponse(apiKey)
    }

    suspend fun createTournament(info : CreateInfo, apiKey: String) : TournamentInfo {
        return api.createTournamentResponse(info, apiKey)
    }

    suspend fun deleteTournament(tournament: String, apiKey: String) {
        api.deleteTournamentResponse(tournament, apiKey)
    }

    suspend fun createParticipant(info: CreateInfo2, tournament: String, apiKey: String) : ChallongeInfo {
        return api.createParticipantResponse(info, tournament, apiKey)
    }

    suspend fun deleteParticipant(tournament: String, id: Int, apiKey: String) {
        api.deleteParticipantResponse(tournament, id, apiKey)
    }

    suspend fun deleteAllParticipant(tournament: String, apiKey: String){
        api.deleteAllParticipantResponse(tournament, apiKey)
    }

    suspend fun randomize(tournament: String, apiKey: String){
        api.randomizeResponse(tournament, apiKey)
    }
}