package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.single_view.*
import kotlin.math.log2
import kotlin.math.pow
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SingleElim : AppCompatActivity(){

    private var roundNumber = 0
    private lateinit var previousRound : ArrayList<String>
    private lateinit var currentRound: ArrayList<Game>
    private lateinit var nextRound : ArrayList<String>

    private fun createOpponents(){
        if(roundNumber == 1){
            previousRound.shuffle()

            val N = log2(previousRound.size.toDouble())
            val NInt = N.toInt()
            val remainder = previousRound.size - 2.toDouble().pow(NInt)

            //Number of participants = 2,4,8,16,32, etc.
            if(remainder == 0.0){
                var i = 0
                var j = 0
                while (i < previousRound.size) {
                    currentRound.add(j, Game(previousRound[i], previousRound[i+1]))
                    j++
                    i += 2
                }
            }

            //Create remainder number of brackets and put rest in nextRound
            if(remainder != 0.0 && remainder / 2.toDouble().pow(NInt) <= 0.5){
                var i = 0
                var j = 0
                while ( j < remainder.toInt()) {
                    currentRound.add(j, Game(previousRound[i], previousRound[i+1]))
                    j++
                    i += 2
                }
                //next round due to leftovers. Screw the bracket, we're giving them random here
                for(k in i.until(previousRound.size)){
                    nextRound.add(previousRound[k])
                }
            }

            //Just create bracket with BYES if bigger
            if(remainder / 2.toDouble().pow(NInt) > 0.5){
                val numBye = 2.toDouble().pow(NInt + 1) - previousRound.size
                for(a in 0.until(numBye.toInt())){
                    previousRound.add("BYE")
                }
                var i = 0
                var j = 0
                while (i < previousRound.size) {
                    currentRound.add(j, Game(previousRound[i], previousRound[i+1]))
                    j++
                    i += 2
                }
            }


        }
        else{ //Round not 1, so bracket is always in squares of 2
            var i = 0
            var j = 0
            while (i < previousRound.size) {
                currentRound.add(j, Game(previousRound[i], previousRound[i+1]))
                j++
                i += 2
            }
        }

        //Make the recyclerView Here
        createRecyclerView()
    }

    private fun createRecyclerView(){
        val rv = findViewById<RecyclerView>(R.id.recyclerViewBracket)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var adapter = SingleElimAdapter(currentRound){
            nextRound.add(it)
            Log.d("next list", nextRound.toString())
        }
        rv.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_view)

        bracket_type_title.text = "Single Elimination"
        roundNumber = intent.getIntExtra("round", 0)
        previousRound = intent.getStringArrayListExtra("list")
        round_number.text = "Winners Round " + roundNumber + ", " + previousRound.size.toString() + " total competitors."
        currentRound = ArrayList()
        nextRound = ArrayList()

        //Need to fix issue with not showing correctly some stuff with going back and creating another instead of getting right activity.
        previous_button.setOnClickListener {
            if(roundNumber == 1){
                Toast.makeText(this, "This is the first Round!", Toast.LENGTH_LONG).show()
            }
            super.onBackPressed()
        }
        next_button.setOnClickListener{
            val N = log2(previousRound.size.toDouble()).toInt()
            var neededListLength = (2.toDouble().pow(N) / 2).toInt()
            val remainder = previousRound.size - 2.toDouble().pow(N).toInt()

            if(roundNumber == 1 && remainder != 0 && remainder / 2.toDouble().pow(N) <= 0.5){
                neededListLength = remainder
            }
            Log.d("calc", "remainder: " + remainder.toString() + "neededListLength: " + neededListLength.toString())
            if(nextRound.size == previousRound.size - neededListLength){
                //winner
                if(neededListLength == 1 && nextRound.size == 1){
                    val intent = Intent(this, Winner::class.java)
                    intent.putExtra("winner", nextRound[0])
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this, SingleElim::class.java)
                    intent.putExtra("list", nextRound)
                    intent.putExtra("round", roundNumber + 1)
                    startActivity(intent)
                }
            }
            else{
                Toast.makeText(this, "Not all rounds have been recorded yet.", Toast.LENGTH_LONG).show()
            }
        }
        createOpponents()
    }
}