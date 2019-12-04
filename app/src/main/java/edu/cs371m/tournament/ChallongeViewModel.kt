package edu.cs371m.tournament

import android.util.Log
import android.widget.Toast
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
import okhttp3.Dispatcher
import retrofit2.Call
import kotlin.coroutines.coroutineContext

class ChallongeViewModel : ViewModel(){

    private val challongeAPI = ChallongeApi.create()
    private val challongeRepo = Repository(challongeAPI)
    private val challongeInfo = MutableLiveData<List<ChallongeInfo>>()
    private val tournamentInfo = MutableLiveData<List<TournamentInfo>>()
    private var url = MutableLiveData<String>().apply{
        value = "!null"
    }

    fun returnURL() : String{
        return url.value.toString()
    }

    fun observeTournamentInfo() : LiveData<List<TournamentInfo>> {
        return tournamentInfo
    }

    fun netRefreshTournament(){
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                + Dispatchers.IO){
            tournamentInfo.postValue(challongeRepo.getTournament())
        }
    }

    fun observeChallongeInfo() : LiveData<List<ChallongeInfo>> {
        return challongeInfo
    }

    fun netRefreshChallonge(){
        if(url.value.toString() == "!null"){
            Log.d("debug", "nothing happened")
        }
        else{
            viewModelScope.launch(
                context = viewModelScope.coroutineContext
                        + Dispatchers.IO){
                challongeInfo.postValue(challongeRepo.getChallonge(url.value.toString()))
            }
        }
    }

    fun chosenTournament(string: String){
        url.value = string
    }

}