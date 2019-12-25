package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.double_view.*
import kotlin.math.log2
import kotlin.math.pow

class DoubleElim : AppCompatActivity(){
    private var roundNumber = 0
    private lateinit var previousRoundWinner : ArrayList<String>
    private lateinit var previousRoundLoser : ArrayList<String>
    private lateinit var currentRoundWinner : ArrayList<Game>
    private lateinit var currentRoundLoser : ArrayList<Game>
    private lateinit var nextRoundWinner : ArrayList<String>
    private lateinit var nextRoundLoser :  ArrayList<String>

    private fun titleSearch() {
        val titleBar = findViewById<EditText>(R.id.actionSearch)
        titleBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isEmpty()){
                    MainActivity.hideKeyboardActivity(this@DoubleElim)
                    createRecyclerView(currentRoundWinner, currentRoundLoser)
                }
                else{
                    var filteredWinner : List<Game> = currentRoundWinner.filter{s -> (s.name1.contains(p0, true) || s.name2.contains(p0, true)) }
                    var filteredLoser : List<Game> = currentRoundLoser.filter{s -> (s.name1.contains(p0, true) || s.name2.contains(p0, true)) }
                    createRecyclerView(ArrayList(filteredWinner), ArrayList(filteredLoser))
                }
            }
        })
    }

    private fun createBracket(){
        //Last Game
        if(previousRoundWinner.size == 1 && previousRoundLoser.size == 1){
            val intent = Intent(this, DoubleElimGrandFinals::class.java)
            intent.putExtra("winner", previousRoundWinner[0])
            intent.putExtra("loser", previousRoundLoser[0])
            intent.putExtra("round", 1)
            startActivity(intent)
        }
        else{
            //only winners round, no losers
            if(roundNumber == 1){
                //Make sure bracket is in multiples of 2
                val N = log2(previousRoundWinner.size.toDouble())
                val nIntUpper = N.toInt() + 1
                val numByes = 2.toDouble().pow(nIntUpper).toInt() - previousRoundWinner.size
                Log.d("debug", numByes.toString())

                if(numByes != previousRoundWinner.size){
                    for(i in 0.until(numByes)){
                        previousRoundWinner.add("BYE#" + i.toString())
                    }
                }


                Log.d("debug2", previousRoundWinner.size.toString())
                previousRoundWinner.shuffle()

                //Deal with winner's round only
                var i = 0
                var j = 0
                while (i < previousRoundWinner.size) {
                    currentRoundWinner.add(j, Game(previousRoundWinner[i], previousRoundWinner[i+1], ""))
                    j++
                    i += 2
                }
            }
            else{
                if(roundNumber % 2 == 1 || (previousRoundWinner.size == 1 && previousRoundLoser.size > 1)){
                    //Don't do anything about winners
                    nextRoundWinner = previousRoundWinner

                }
                //Even winners round, so
                if(roundNumber % 2 == 0 && previousRoundWinner.size != 1){
                    var i = 0
                    var j = 0
                    while (i < previousRoundWinner.size) {
                        currentRoundWinner.add(j, Game(previousRoundWinner[i], previousRoundWinner[i+1], ""))
                        j++
                        i += 2
                    }
                }

                //Deal with losers
                var i2 = 0
                var j2 = 0
                while(i2 < previousRoundLoser.size) {
                    currentRoundLoser.add(j2, Game(previousRoundLoser[i2], previousRoundLoser[i2+1], ""))
                    j2++
                    i2 += 2
                }
            }
        }

        //auto win for BYEs
        for(i in 0.until(currentRoundWinner.size)){
                if (currentRoundWinner[i].name1.length >= 3 && currentRoundWinner[i].name1.subSequence(0, 3) == "BYE") {
                    currentRoundWinner[i].winner = currentRoundWinner[i].name2
                }
                if (currentRoundWinner[i].name2.length >= 3 && currentRoundWinner[i].name2.subSequence(0, 3) == "BYE") {
                    currentRoundWinner[i].winner = currentRoundWinner[i].name1
                }
        }
        for(i in 0.until(currentRoundLoser.size)){
                if (currentRoundLoser[i].name1.length >= 3 && currentRoundLoser[i].name1.subSequence(0, 3) == "BYE") {
                    currentRoundLoser[i].winner = currentRoundLoser[i].name2
                }
                if (currentRoundLoser[i].name2.length >= 3 && currentRoundLoser[i].name2.subSequence(0, 3) == "BYE") {
                    currentRoundLoser[i].winner = currentRoundLoser[i].name1
                }
        }
        createRecyclerView(currentRoundWinner, currentRoundLoser)
    }

    private fun createRecyclerView(winner : ArrayList<Game>, loser: ArrayList<Game>){
        //Winners
        if(roundNumber == 1 || roundNumber % 2 == 0){
            val rv = findViewById<RecyclerView>(R.id.recyclerViewBracketWinner)
            rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

            var adapter = SingleElimAdapter(winner, false)
            rv.adapter = adapter
        }

        //Losers
        if(roundNumber != 1){
            val rvLoser = findViewById<RecyclerView>(R.id.recyclerViewBracketLoser)
            rvLoser.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

            var adapter2 = SingleElimAdapter(loser, false)
            rvLoser.adapter = adapter2
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.double_view)

        //Toolbar functionality
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            MainActivity.initActionBar(it, this)
        }
        titleSearch()

        roundNumber = intent.getIntExtra("round", 0)
        previousRoundWinner = intent.getStringArrayListExtra("winner_list")
        previousRoundLoser = intent.getStringArrayListExtra("loser_list")
        bracket_type_title.text = "Double Elimination"

        if(roundNumber % 2 == 0 || roundNumber == 1){
            round_number_winner.text = "Winners Round " + roundNumber + ", " + previousRoundWinner.size.toString() + " total competitors in Winners Bracket."
        }
        else{
            round_number_winner.text = "There is no winner's bracket this round. "
        }

        if(roundNumber == 1){
            round_number_loser.text = "There is no loser's bracket right now."
        }
        else{
            round_number_loser.text = "Losers Round " + (roundNumber - 1)  + ", " + previousRoundLoser.size.toString() + " total competitors in Losers Bracket."
        }

        currentRoundWinner = ArrayList()
        currentRoundLoser = ArrayList()
        nextRoundWinner = ArrayList()
        nextRoundLoser = ArrayList()

        createBracket()

        previous_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
        next_button.setOnClickListener{
            if(roundNumber == 1){
                var allWinner = true
                for(i in 0.until(currentRoundWinner.size)){
                    if(currentRoundWinner[i].winner == ""){
                        allWinner = false
                        break
                    }
                }
                if(!allWinner){
                    Toast.makeText(this, "Not all rounds have been recorded!", Toast.LENGTH_LONG).show()
                }
                else{
                    //need to add to the winners list
                    for(i in 0.until(currentRoundWinner.size)){
                        nextRoundWinner.add(currentRoundWinner[i].winner)
                    }
                    //need to add to the losers list
                    for(i in 0.until(previousRoundWinner.size)){
                        if(!nextRoundWinner.contains(previousRoundWinner[i]) && !nextRoundLoser.contains(previousRoundWinner[i])){
                            nextRoundLoser.add(previousRoundWinner[i])
                        }
                    }
                    //Send to next activity
                    val intent = Intent(this, DoubleElim::class.java)
                    intent.putExtra("winner_list", nextRoundWinner)
                    intent.putExtra("loser_list", nextRoundLoser)
                    intent.putExtra("round", roundNumber + 1)
                    startActivity(intent)
                }
            }
            else{
                //need to add losers to winners
                if(roundNumber % 2 == 0){
                    var allRounds = true
                    for(i in 0.until(currentRoundWinner.size)){
                        if(currentRoundWinner[i].winner == ""){
                            allRounds = false
                            break
                        }
                    }
                    for(i in 0.until(currentRoundLoser.size)){
                        if(currentRoundLoser[i].winner == ""){
                            allRounds = false
                            break
                        }
                    }
                    if(!allRounds){
                        Toast.makeText(this, "Not all rounds have been recorded!", Toast.LENGTH_LONG).show()
                    }
                    else{
                        //add winners of winners bracket
                        for(i in 0.until(currentRoundWinner.size)){
                            nextRoundWinner.add(currentRoundWinner[i].winner)
                        }
                        //add winners of losers bracket
                        for(i in 0.until(currentRoundLoser.size)){
                            nextRoundLoser.add(currentRoundLoser[i].winner)
                        }
                        //add loser of winners to losers bracket
                        for(i in 0.until(previousRoundWinner.size)){
                            if(!nextRoundWinner.contains(previousRoundWinner[i]) && !nextRoundLoser.contains(previousRoundWinner[i])){
                                nextRoundLoser.add(previousRoundWinner[i])
                            }
                        }
                        //Send to next activity
                        val intent = Intent(this, DoubleElim::class.java)
                        intent.putExtra("winner_list", nextRoundWinner)
                        intent.putExtra("loser_list", nextRoundLoser)
                        intent.putExtra("round", roundNumber + 1)
                        startActivity(intent)
                    }
                }
                //then good, only need losers ROund
                if(roundNumber % 2 == 1){
                    var allRecorded = true
                    for(i in 0.until(currentRoundLoser.size)){
                        if(currentRoundLoser[i].winner == ""){
                            allRecorded = false
                        }
                    }
                    if(!allRecorded){
                        Toast.makeText(this, "Not all rounds have been recorded!", Toast.LENGTH_LONG).show()
                    }
                    else{
                        for(i in 0.until(currentRoundLoser.size)){
                            nextRoundLoser.add(currentRoundLoser[i].winner)
                        }
                        nextRoundWinner = previousRoundWinner
                        //Send to next activity
                        val intent = Intent(this, DoubleElim::class.java)
                        intent.putExtra("winner_list", nextRoundWinner)
                        intent.putExtra("loser_list", nextRoundLoser)
                        intent.putExtra("round", roundNumber + 1)
                        startActivity(intent)
                    }
                }
            }
        }

    }
}