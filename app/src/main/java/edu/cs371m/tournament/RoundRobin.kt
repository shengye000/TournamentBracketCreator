package edu.cs371m.tournament

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
    private lateinit var previousRound : ArrayList<String>
    private lateinit var currentRound : ArrayList<RoundRobinGame>
    private lateinit var currentRoundCalc : ArrayList<RoundRobinData>
    private lateinit var nextRound : ArrayList<String>

    private fun makeBracket(){
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
        previousRound.shuffle()

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
            currentRoundCalc.add(it)
            Log.d("next list", currentRoundCalc.toString())
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
            if((previousRound.size * 1.5).toInt() == currentRoundCalc.size){
                //sort by number
                var tempList = (currentRoundCalc).sortedWith(compareBy({it.position}, {it.s1}))
                currentRoundCalc = ArrayList(tempList)
                Log.d("debug", currentRoundCalc.toString())

//                var j = 0
//                while (j < currentRoundCalc.size) {
//                    if(j % 6 == 0){
//
//                    }
//                    j++
//                }
            }
            else{
                Toast.makeText(this, "Not all rounds have been recorded", Toast.LENGTH_LONG).show()
            }
        }
        makeBracket()
        createRecyclerView()
    }
}