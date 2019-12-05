package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

                if(numByes != previousRoundWinner.size){
                    for(i in 0.until(numByes)){
                        previousRoundWinner.add("BYE")
                    }
                }
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
        createRecyclerView()
    }

    private fun createRecyclerView(){
        //Winners
        if(roundNumber == 1 || roundNumber % 2 == 0){
            val rv = findViewById<RecyclerView>(R.id.recyclerViewBracketWinner)
            rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

            var adapter = SingleElimAdapter(currentRoundWinner)
            rv.adapter = adapter
        }

        //Losers
        if(roundNumber != 1){
            val rvLoser = findViewById<RecyclerView>(R.id.recyclerViewBracketLoser)
            rvLoser.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

            var adapter2 = SingleElimAdapter(currentRoundLoser)
            rvLoser.adapter = adapter2
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.double_view)

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

        previous_button.setOnClickListener {
            if(roundNumber == 1){
                Toast.makeText(this, "This is the first Round!", Toast.LENGTH_LONG).show()
            }
            else{
                super.onBackPressed()
            }
        }
        next_button.setOnClickListener{
            if(roundNumber == 1){
                if(nextRoundWinner.size != previousRoundWinner.size / 2){
                    Toast.makeText(this, "Not all rounds have been recorded!", Toast.LENGTH_LONG).show()
                }
                else{
                    //need to add the losers list
                    for(i in 0.until(previousRoundWinner.size)){
                        if(!nextRoundWinner.contains(previousRoundWinner[i])){
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
                    if(nextRoundWinner.size == previousRoundWinner.size / 2 && nextRoundLoser.size == previousRoundLoser.size / 2){
                        for(i in 0.until(previousRoundWinner.size)){
                            if(!nextRoundLoser.contains(previousRoundWinner[i]) && !nextRoundWinner.contains(previousRoundWinner[i])){
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
                    else{
                        Toast.makeText(this, "Not all rounds have been recorded!", Toast.LENGTH_LONG).show()
                    }

                }
                //then good
                if(roundNumber % 2 == 1){
                    if(nextRoundWinner.size == previousRoundWinner.size && nextRoundLoser.size == previousRoundLoser.size / 2){
                        //Send to next activity
                        val intent = Intent(this, DoubleElim::class.java)
                        intent.putExtra("winner_list", nextRoundWinner)
                        intent.putExtra("loser_list", nextRoundLoser)
                        intent.putExtra("round", roundNumber + 1)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Not all rounds have been recorded!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        createBracket()
    }
}