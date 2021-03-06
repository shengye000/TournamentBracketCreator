package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.tournament.api.TournamentInfo
import kotlinx.android.synthetic.main.tournament_view.*

class Tournament : AppCompatActivity(){

    private lateinit var viewModel: ChallongeViewModel
    private lateinit var tournamentList : List<TournamentInfo>
    private var chosenTournamentURL = ""
    private var apiKey = ""

    private fun getInfo(apiKey: String) {
        viewModel.chosenapiKey(apiKey)

        viewModel.observeTournamentInfo().observe(this, Observer{
            tournamentList = it
            if(tournamentList.isNotEmpty()){
                val rv = findViewById<RecyclerView>(R.id.recyclerViewTournament)
                rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

                var adapter = TournamentAdapter(tournamentList){
                    chosenTournamentURL = it
                    Log.d("debug",chosenTournamentURL)
                    chosen_tournament.text = "Tournament URL: " + chosenTournamentURL
                }
                rv.adapter = adapter
            }
        })
        viewModel.netRefreshTournament()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tournament_view)

        //Toolbar functionality
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            MainActivity.initActionBar(it, this)
        }

        login_button.setOnClickListener {
            //Login with username and password
            if(challonge_username.text.isNotEmpty() && challonge_password.text.isNotEmpty()){
                apiKey = challonge_password.text.toString()
                getInfo(apiKey)
            }
            else{
                Toast.makeText(this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_LONG).show()
            }
        }
        default_api_button.setOnClickListener {
            apiKey = "cTKUsryOZVAkixOD6I1PCf3PsRRJAWjvM1ahkd7Q"
            getInfo(apiKey)
        }

        chosen_tournament.text = "Tournament URL: " + chosenTournamentURL
        button.setOnClickListener {
            if(chosenTournamentURL == ""){
                Toast.makeText(this, "NO TOURNAMENT CHOSEN", Toast.LENGTH_LONG).show()
            }
            else{
                val intent = Intent(this, Challonge::class.java)
                Log.d("debug", "in Tournament" + viewModel.returnURL())
                intent.putExtra("url_string", chosenTournamentURL)
                intent.putExtra("api_string", apiKey)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
        button_cancel.setOnClickListener {
            //Delete the tournament selected
            if(chosenTournamentURL == ""){
                Toast.makeText(this, "NO TOURNAMENT CHOSEN", Toast.LENGTH_LONG).show()
            }
            else{
                viewModel.deleteTournamentInfo(chosenTournamentURL)
                Thread.sleep(1000)
                getInfo(apiKey)
            }
        }

        viewModel = ViewModelProviders.of(this)[ChallongeViewModel::class.java]
    }
}