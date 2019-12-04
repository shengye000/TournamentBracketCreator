package edu.cs371m.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cs371m.tournament.api.ChallongeApi
import edu.cs371m.tournament.api.ChallongeInfo
import edu.cs371m.tournament.api.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import kotlin.coroutines.coroutineContext

class ChallongeViewModel : ViewModel(){

    private val challongeAPI = ChallongeApi.create()
    private val challongeRepo = Repository(challongeAPI)
    private val challongeInfo = MutableLiveData<List<ChallongeInfo>>()

    fun observeChallongeInfo() : LiveData<List<ChallongeInfo>> {
        return challongeInfo
    }

    fun netRefresh(){
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO){
            challongeInfo.postValue(challongeRepo.getChallonge())
        }
    }

}