package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_view.*

class RoundRobinTieBreaker : AppCompatActivity(){
    private var roundNumber = 0
    private lateinit var currentList : ArrayList<RoundRobinData>
    private lateinit var calculationList : ArrayList<RoundRobinTieBreakerData>
    private lateinit var nextList : ArrayList<String>

    private fun tieBreak(){
        var firstString = "!null"
        var secondString = "!null"
        var thirdString = "!null"
        var threeExist = false
        var edited = false
        for(i in 0.until(currentList.size)){
            //reset every 4 indexes
            if(i % 4 == 0) {
                firstString = "!null"
                secondString = "!null"
                thirdString = "!null"
                threeExist = false
            }

            //if one bracket did not need a tiebreaker
            if(currentList[i].num == 3){
                firstString = "!null"
                secondString = "!null"
                thirdString = "!null"
                threeExist = true
                nextList.add(currentList[i].s1)
            }

            //Set the buttons. Fix problem
            if(currentList[i].num == 2 && firstString == "!null" && !threeExist){
                firstString = currentList[i].s1
                edited = true
            }
            if(currentList[i].num == 2 && secondString == "!null" && !edited && !threeExist){
                secondString = currentList[i].s1
                edited = true
            }
            if(currentList[i].num == 2 && thirdString == "!null" && !edited && !threeExist){
                thirdString = currentList[i].s1
            }
            edited = false
            //Check at end
            if(i % 4 == 3 && !threeExist){
                calculationList.add(RoundRobinTieBreakerData(firstString, secondString, thirdString))
            }
        }
    }

    private fun createRecyclerView(){
        val rv = findViewById<RecyclerView>(R.id.recyclerViewBracket)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var adapter = RoundRobinTieAdapter(calculationList){
            nextList.add(it)
            Log.d("next list", nextList.toString())
        }
        rv.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_view)

        //Toolbar functionality
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            MainActivity.initActionBar(it, this)
        }

        roundNumber = intent.getIntExtra("round", 0)
        currentList = intent.getSerializableExtra("list") as ArrayList<RoundRobinData>
        calculationList = ArrayList()
        nextList = ArrayList()

        bracket_type_title.text = "R.R. TIEBREAKER"

        previous_button.setOnClickListener {
            Toast.makeText(this, "Cannot go back", Toast.LENGTH_LONG).show()
        }
        next_button.setOnClickListener {
            if(currentList.size / 4 == nextList.size){
                //winner
                if(nextList.size == 1){
                    val intent = Intent(this, Winner::class.java)
                    intent.putExtra("winner", nextList[0])
                    startActivity(intent)
                }
                //need more rounds
                else{
                    val intent = Intent(this, RoundRobin::class.java)
                    intent.putExtra("list", nextList)
                    intent.putExtra("round", roundNumber + 1)
                    startActivity(intent)
                }
            }
            else{
                Toast.makeText(this, "Not all tiebreakers selected!", Toast.LENGTH_LONG).show()
            }
        }
        tieBreak()
        createRecyclerView()
    }
}