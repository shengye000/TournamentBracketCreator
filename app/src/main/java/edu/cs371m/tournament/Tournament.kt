package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.tournament.api.ChallongeInfo
import edu.cs371m.tournament.api.TournamentInfo
import kotlinx.android.synthetic.main.tournament_view.*

class Tournament : AppCompatActivity(){

    private lateinit var viewModel: ChallongeViewModel
    private lateinit var tournamentList : List<TournamentInfo>
    private var chosenTournamentURL = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tournament_view)

        chosen_tournament.text = "Tournament URL: " + chosenTournamentURL
        button.setOnClickListener {
            if(chosenTournamentURL == ""){
                Toast.makeText(this, "NO TOURNAMENT CHOSEN", Toast.LENGTH_LONG).show()
            }
            else{
                val intent = Intent(this, Challonge::class.java)
                Log.d("debug", "in Tournament" + viewModel.returnURL())
                intent.putExtra("url_string", chosenTournamentURL)
                startActivity(intent)
            }
        }
        button_cancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        viewModel = ViewModelProviders.of(this)[ChallongeViewModel::class.java]
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
}