package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import java.io.Serializable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_view.*



class PreLim2 : AppCompatActivity(), Serializable{

    //top 5 are to pass as activity stuff
    private var totalRounds = 0
    private var roundNumber = 0
    private var roundsToAdvance = 0
    private var weighed = true
    private lateinit var previousRound : ArrayList<PreLimData>

    private lateinit var currentRound: ArrayList<Game>
    private lateinit var nextRound : ArrayList<PreLimData>
    private lateinit var listOfWinners : ArrayList<String>

    private fun createOpponents(){
        // Pair base on same win-loss ratio, hopefully this really works!
        if(weighed){
            if(roundNumber == 1){
               previousRound.shuffle()
            }
            else{
                previousRound.sortedWith(compareBy({it.wins}))
            }


            var i = 0
            var j = 0
            while (i < previousRound.size) {
                currentRound.add(j, Game(previousRound[i].player, previousRound[i+1].player))
                j++
                i += 2
            }
        }
        else{

            previousRound.shuffle()

            var i = 0
            var j = 0
            while (i < previousRound.size) {
                currentRound.add(j, Game(previousRound[i].player, previousRound[i+1].player))
                j++
                i += 2
            }
        }
        createRecyclerView()
    }

    private fun createRecyclerView(){
        val rv = findViewById<RecyclerView>(R.id.recyclerViewBracket)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var adapter = SingleElimAdapter(currentRound){
            listOfWinners.add(it)
            Log.d("next list", listOfWinners.toString())
        }
        rv.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_view)

        bracket_type_title.text = "Pre-Elimination"
        roundNumber = intent.getIntExtra("round", 0)
        previousRound = intent.getSerializableExtra("list") as ArrayList<PreLimData>
        totalRounds = intent.getIntExtra("total", 0)
        roundsToAdvance = intent.getIntExtra("needed", 0)
        weighed = intent.getBooleanExtra("weighed", true)
        round_number.text = "Pre-Elim Round " + roundNumber + ", " + previousRound.size.toString() + " total competitors."
        currentRound = ArrayList()
        nextRound = ArrayList()
        listOfWinners = ArrayList()

        //Need a Bye if round number is odd
        if(roundNumber == 1 && previousRound.size % 2 == 1){
            previousRound.add(PreLimData("BYE", 0))
        }

        //Need to fix issue with not showing correctly some stuff with going back and creating another instead of getting right activity.
        previous_button.setOnClickListener {
            if(roundNumber == 1){
                Toast.makeText(this, "This is the first Round!", Toast.LENGTH_LONG).show()
            }
            else{
                super.onBackPressed()
            }
        }
        next_button.setOnClickListener{
            //Go to single elimination with the correct number of wins
            if(roundNumber == totalRounds && previousRound.size /2 == listOfWinners.size){
                Toast.makeText(this, "Single Elimination Starting", Toast.LENGTH_LONG).show()

                //Update List
                nextRound = previousRound
                for(i in 0.until(nextRound.size)){
                    val player = nextRound[i].player
                    if(listOfWinners.contains(player)){
                        nextRound[i].wins++
                    }
                }

                var singleElimList = ArrayList<String>()
                for((key, value) in nextRound){
                    if(value >= roundsToAdvance){
                        singleElimList.add(key)
                    }
                }
                //go to singleElim
                val intent = Intent(this, SingleElim::class.java)
                intent.putExtra("round", 1)
                intent.putExtra("list",singleElimList)
                startActivity(intent)
            }
            else{ //Still need more prelim rounds
                if(previousRound.size / 2 != listOfWinners.size){
                    Toast.makeText(this, "Not all rounds have been recorded!", Toast.LENGTH_LONG).show()
                }
                else{
                    //Update List
                    nextRound = previousRound
                    for(i in 0.until(nextRound.size)){
                        val player = nextRound[i].player
                        if(listOfWinners.contains(player)){
                            nextRound[i].wins++
                        }
                    }

                    //Create new activity PreLim 2
                    val intent = Intent(this, PreLim2::class.java)
                    intent.putExtra("list", nextRound)
                    intent.putExtra("total", totalRounds)
                    intent.putExtra("needed", roundsToAdvance)
                    intent.putExtra("weighed", weighed)
                    intent.putExtra("round", roundNumber + 1)
                    startActivity(intent)
                }
            }
        }
        createOpponents()
    }
}