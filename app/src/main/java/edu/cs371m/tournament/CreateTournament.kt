package edu.cs371m.tournament

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.create_view.*
import kotlinx.android.synthetic.main.tournament_view.*
import androidx.lifecycle.Observer

class CreateTournament : AppCompatActivity(){
    private lateinit var viewModel: ChallongeViewModel
    private var apiKey = ""
    private var createdName = ""
    private var tournamentType = ""
    private var createdDescription = ""
    private var createdURL = ""

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_single ->
                    if (checked) {
                        tournamentType = "single elimination"
                    }
                R.id.radio_double ->
                    if (checked) {
                        tournamentType = "double elimination"
                    }
                R.id.radio_rr ->
                    if (checked) {
                        tournamentType = "round robin"
                    }
                R.id.radio_swiss ->
                    if (checked) {
                        tournamentType = "swiss"
                    }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_view)

        //Toolbar functionality
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            MainActivity.initActionBar(it, this)
        }

        viewModel = ViewModelProviders.of(this)[ChallongeViewModel::class.java]

        create_view_button.setOnClickListener {
            apiKey = ""
            viewModel.chosenapiKey(apiKey)

            createdName = create_view_name.text.toString()
            createdDescription = create_view_description.text.toString()

            val alphabet: List<Char> = ('a'..'z') + ('0'..'9')
            createdURL = List(8) { alphabet.random() }.joinToString("")
            Log.d("url", createdURL)

            viewModel.createObserveTournamentInfo().observe(this, Observer{
                create_view_result.text = it.toString()
            })
            viewModel.netRefreshCreateTournament(createdName, createdURL, tournamentType, createdDescription)

        }

    }

}