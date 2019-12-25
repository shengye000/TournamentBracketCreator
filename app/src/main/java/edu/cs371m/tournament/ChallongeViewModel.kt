package edu.cs371m.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cs371m.tournament.api.ChallongeApi
import edu.cs371m.tournament.api.ChallongeInfo
import edu.cs371m.tournament.api.Repository
import edu.cs371m.tournament.api.TournamentInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallongeViewModel : ViewModel(){

    private val challongeAPI = ChallongeApi.create()
    private val challongeRepo = Repository(challongeAPI)
    private val challongeInfo = MutableLiveData<List<ChallongeInfo>>()
    private val tournamentInfo = MutableLiveData<List<TournamentInfo>>()
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


}