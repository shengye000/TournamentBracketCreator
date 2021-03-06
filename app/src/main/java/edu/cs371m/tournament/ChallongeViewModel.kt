package edu.cs371m.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cs371m.tournament.api.*
import edu.cs371m.tournament.api.CreateTournament
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallongeViewModel : ViewModel(){

    private val challongeAPI = ChallongeApi.create()
    private val challongeRepo = Repository(challongeAPI)
    private val challongeInfo = MutableLiveData<List<ChallongeInfo>>()
    private val tournamentInfo = MutableLiveData<List<TournamentInfo>>()
    private val createTournamentInfo = MutableLiveData<TournamentInfo>()
    private val createParticipantInfo = MutableLiveData<ChallongeInfo>()

    private var url = MutableLiveData<String>().apply{
        value = ""
    }
    private var apiKey = MutableLiveData<String>().apply{
        value = ""
    }

    fun returnapiKey() : String{
        return apiKey.value.toString()
    }
    fun chosenapiKey(string: String){
        apiKey.value = string
    }
    fun returnURL() : String{
        return url.value.toString()
    }

    fun chosenTournament(string: String){
        url.value = string
    }

    fun observeCreateParticipantInfo() : LiveData<ChallongeInfo> {
        return createParticipantInfo
    }

    fun netRefreshCreateParticipant(s1: String, i1: Int){
        if(apiKey.value.toString() != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                createParticipantInfo.postValue(challongeRepo.createParticipant(CreateInfo2(CreateParticipants(s1, i1)), url.value.toString(), apiKey.value.toString()))
            }
        }
    }

    fun createObserveTournamentInfo() : LiveData<TournamentInfo> {
        return createTournamentInfo
    }

    fun netRefreshCreateTournament(s1: String, s2: String, s3: String, s4: String){
        if(apiKey.value.toString() != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                createTournamentInfo.postValue(challongeRepo.createTournament(CreateInfo(CreateTournament(s1, s2, s3, s4)), apiKey.value.toString()))
            }
        }
    }

    fun deleteTournamentInfo(tourney: String){
        if(apiKey.value.toString() != "" && tourney != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                challongeRepo.deleteTournament(tourney, apiKey.value.toString())
            }
        }
    }

    fun deleteParticipantInfo(tourney: String, id: Int){
        if(apiKey.value.toString() != "" && tourney != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                challongeRepo.deleteParticipant(tourney, id, apiKey.value.toString())
            }
        }
    }

    fun observeTournamentInfo() : LiveData<List<TournamentInfo>> {
        return tournamentInfo
    }

    fun netRefreshTournament(){
        if(apiKey.value.toString() != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                tournamentInfo.postValue(challongeRepo.getTournament(apiKey.value.toString()))
            }
        }
    }

    fun observeChallongeInfo() : LiveData<List<ChallongeInfo>> {
        return challongeInfo
    }

    fun netRefreshChallonge(){
        if(url.value.toString() != "" && apiKey.value.toString() != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                challongeInfo.postValue(challongeRepo.getChallonge(url.value.toString(), apiKey.value.toString()))
            }
        }
    }

    fun deleteAllParticipantInfo(tourney: String){
        if(apiKey.value.toString() != "" && tourney != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                challongeRepo.deleteAllParticipant(tourney, apiKey.value.toString())
            }
        }
    }

    fun randomizeInfo(tourney: String){
        if(apiKey.value.toString() != "" && tourney != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                challongeRepo.randomize(tourney, apiKey.value.toString())
            }
        }
    }

    fun bulkAddParticipantInfo(tourney: String, listParticipant: ArrayList<String>){
        if(apiKey.value.toString() != "" && tourney != ""){
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                challongeRepo.bulkAddParticipant(tourney, listParticipant, apiKey.value.toString())
            }
        }
    }
}