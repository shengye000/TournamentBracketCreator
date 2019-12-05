package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_view.*
import kotlin.math.log
import kotlin.math.log2
import kotlin.math.pow

class RoundRobin : AppCompatActivity(){
    private var roundNumber = 0
    private var totalMatches = 0
    private lateinit var previousRound : ArrayList<String>
    private lateinit var currentRound : ArrayList<RoundRobinGame>
    private lateinit var currentRoundCalc : ArrayList<RoundRobinData>
    private lateinit var nextRound : ArrayList<String>

    private fun makeBracket(){
        previousRound.shuffle()
        if(roundNumber == 1){
            //add BYEs to score
            val N = log(previousRound.size.toDouble(), 4.0)
            var nInt = N.toInt()
            if(N % 1 != 0.toDouble()){
                nInt++
            }
            val numByes = 4.toDouble().pow(nInt).toInt() - previousRound.size
            if(numByes != previousRound.size){
                for(i in 0.until(numByes)){
                    previousRound.add("BYE #" + i.toString())
                }
            }
        }
        //shuffle then have them in the right place in currentRoundCalc
        for(k in 0.until(previousRound.size)){
            currentRoundCalc.add(k, RoundRobinData(previousRound[k], 0))
        }

        var i = 0
        var j = 0
        while (i < previousRound.size) {
            currentRound.add(j, RoundRobinGame(previousRound[i], previousRound[i+1], previousRound[i+2], previousRound[i+3]))
            j++
            i += 4
        }

    }

    private fun createRecyclerView(){
        val rv = findViewById<RecyclerView>(R.id.recyclerViewBracket)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var adapter = RoundRobinAdapter(currentRound){
            for(i in 0.until(currentRoundCalc.size)){
                if(currentRoundCalc[i].s1 == it){
                    currentRoundCalc[i].num++
                    totalMatches++
                    break
                }
            }
            //Log.d("next list", currentRoundCalc.toString())
        }
        rv.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_view)

        roundNumber = intent.getIntExtra("round", 0)
        previousRound = intent.getStringArrayListExtra("list")
        bracket_type_title.text = "Round Robin Round " + roundNumber.toString()
        currentRound = ArrayList()
        currentRoundCalc = ArrayList()
        nextRound = ArrayList()

        previous_button.setOnClickListener {
            if(roundNumber == 1){
                Toast.makeText(this, "This is the first Round!", Toast.LENGTH_LONG).show()
            }
            else{
                super.onBackPressed()
            }
        }
        next_button.setOnClickListener {
            if((previousRound.size * 1.5).toInt() == totalMatches){
                //See if tiebreakers are needed.
                var totalThree = 0
                for(i in 0.until(currentRoundCalc.size)){
                    if(currentRoundCalc[i].num == 3){
                        totalThree++
                    }
                }
                //need tiebreaker
                if(currentRoundCalc.size / 4 > totalThree){
                    val intent = Intent(this, RoundRobinTieBreaker::class.java)
                    intent.putExtra("round", roundNumber)
                    intent.putExtra("list", currentRoundCalc)
                    startActivity(intent)
                }
                else{
                    //last round so winner
                    if(currentRoundCalc.size == 4){
                        var winnerString = "null"
                        for(i in 0.until(currentRoundCalc.size)){
                            if(currentRoundCalc[i].num == 3){
                                winnerString = currentRoundCalc[i].s1
                                break
                            }
                        }
                        val intent = Intent(this, Winner::class.java)
                        intent.putExtra("winner", winnerString)
                        startActivity(intent)
                    }
                    //need more rounds
                    else{
                        var i = 0
                        while (i < currentRoundCalc.size) {
                            if(currentRoundCalc[i].num == 3){
                                nextRound.add(currentRoundCalc[i].s1)
                            }
                            i++
                        }
                        //Log.d("debug", nextRound.toString())
                        val intent = Intent(this, RoundRobin::class.java)
                        intent.putExtra("list", nextRound)
                        intent.putExtra("round", roundNumber + 1)
                        startActivity(intent)
                    }
                }
            }
            else{
                Toast.makeText(this, "Not all rounds have been recorded", Toast.LENGTH_LONG).show()
            }
        }
        makeBracket()
        createRecyclerView()
    }
}