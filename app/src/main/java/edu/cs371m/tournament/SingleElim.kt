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
import kotlinx.android.synthetic.main.single_view.*
import kotlin.math.log2
import kotlin.math.pow


class SingleElim : AppCompatActivity(){

    private var roundNumber = 0
    private lateinit var previousRound : ArrayList<String>
    private lateinit var currentRound: ArrayList<Game>
    private lateinit var nextRound : ArrayList<String>
    private lateinit var rv: RecyclerView
    private lateinit var filteredList: List<Game>
    private lateinit var results : ArrayList<List<Game>>
    private var previousStatus = false

    private fun titleSearch() {
        val titleBar = findViewById<EditText>(R.id.actionSearch)
        titleBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isEmpty()){
                    MainActivity.hideKeyboardActivity(this@SingleElim)
                    filteredList= currentRound.filterNot { it.name1.startsWith("BYE") && it.name2.startsWith("BYE") }
                    createRecyclerView(ArrayList(filteredList))
                }
                else{
                    var filteredListSearch = filteredList.filter{s -> (s.name1.contains(p0, true) || s.name2.contains(p0, true))}
                    createRecyclerView(ArrayList(filteredListSearch))
                }
            }
        })
    }

    private fun createOpponents(){
        if(roundNumber == 1){
            previousRound.shuffle()
            //Make sure bracket is in multiples of 2
            val N = log2(previousRound.size.toDouble())
            val nIntUpper = N.toInt() + 1
            val numByes = 2.toDouble().pow(nIntUpper).toInt() - previousRound.size

            if(numByes != previousRound.size){
                for(i in 0.until(numByes)){
                    previousRound.add("BYE")
                }
            }
            //Add to list
            var i = 0
            var j = 0
            while (i < previousRound.size) {
                currentRound.add(j, Game(previousRound[i], previousRound[i+1], ""))
                j++
                i += 2
            }
        }
        else{ //Round not 1, so bracket is always in squares of 2
            var i = 0
            var j = 0
            while (i < previousRound.size) {
                currentRound.add(j, Game(previousRound[i], previousRound[i+1],""))
                j++
                i += 2
            }
        }

        for(i in 0.until(currentRound.size)){
            if (currentRound[i].name1.length >= 3 && currentRound[i].name1.subSequence(0, 3) == "BYE") {
                Log.d("debug", "inside 1")
                currentRound[i].winner = currentRound[i].name2
            }
            if (currentRound[i].name2.length >=3 && currentRound[i].name2.subSequence(0, 3) == "BYE") {
                Log.d("debug", "inside 2")
                currentRound[i].winner = currentRound[i].name1
            }
        }

        //Make the recyclerView Here
        filteredList= currentRound.filterNot { it.name1.startsWith("BYE") && it.name2.startsWith("BYE") }
        createRecyclerView(ArrayList(filteredList))
    }

    private fun createRecyclerView(list: ArrayList<Game>){
        rv = findViewById(R.id.recyclerViewBracket)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var adapter = SingleElimAdapter(list, previousStatus)
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
        titleSearch()

        currentRound = ArrayList()
        nextRound = ArrayList()
        bracket_type_title.text = "Single Elimination"
        roundNumber = intent.getIntExtra("round", 0)
        previousRound = intent.getStringArrayListExtra("list")
        //TODO: Figure out number of participants remaining and add to text below
        round_number.text = "Winners Round " + roundNumber
        results = intent.getSerializableExtra("result") as ArrayList<List<Game>>

        //previous round stuff
        previousStatus = roundNumber <= results.size
        if(previousStatus){
            currentRound = ArrayList(results[roundNumber - 1])
            filteredList= currentRound.filterNot { it.name1.startsWith("BYE") && it.name2.startsWith("BYE") }
            createRecyclerView(ArrayList(filteredList))
        }
        else{
            createOpponents()
        }

        //Need to fix issue with not showing correctly some stuff with going back and creating another instead of getting right activity.
        previous_button.setOnClickListener {
            if(roundNumber == 1){
                Toast.makeText(this, "This is the first round!", Toast.LENGTH_LONG).show()
            }
            else{
                val intent = Intent(this, SingleElim::class.java)
                intent.putExtra("list", nextRound)
                intent.putExtra("round", roundNumber - 1)
                intent.putExtra("result", results)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
        next_button.setOnClickListener{
            var allRecorded = true
            for(i in 0.until(currentRound.size)){
                if(currentRound[i].winner == ""){
                    allRecorded = false
                    break
                }
            }
            if(!allRecorded){
                Toast.makeText(this, "Not all round have been recorded!", Toast.LENGTH_LONG).show()
            }
            else{
                for(j in 0.until(currentRound.size)){
                    nextRound.add(currentRound[j].winner)
                }
                if(!previousStatus){
                    results.add(roundNumber-1, currentRound)
                }
                //winner
                if(nextRound.size == 1){
                    val intent = Intent(this, Winner::class.java)
                    intent.putExtra("winner", nextRound[0])
                    intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
                else{
                    val intent = Intent(this, SingleElim::class.java)
                    intent.putExtra("list", nextRound)
                    intent.putExtra("round", roundNumber + 1)
                    intent.putExtra("result", results)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}