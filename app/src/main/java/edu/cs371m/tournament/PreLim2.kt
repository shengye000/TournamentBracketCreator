package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import java.io.Serializable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

    private fun titleSearch() {
        val titleBar = findViewById<EditText>(R.id.actionSearch)
        titleBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isEmpty()){
                    MainActivity.hideKeyboardActivity(this@PreLim2)
                    createRecyclerView(ArrayList(currentRound))
                }
                else{
                    var currentRoundFilter = currentRound.filter{s -> (s.name1.contains(p0, true) || s.name2.contains(p0, true))}
                    createRecyclerView(ArrayList(currentRoundFilter))
                }
            }
        })
    }

    private fun createOpponents(){
        // Pair base on same win-loss ratio, hopefully this really works!
        if(weighed){
            if(roundNumber == 1){
               previousRound.shuffle()
                var i = 0
                var j = 0
                while (i < previousRound.size) {
                    currentRound.add(j, Game(previousRound[i].player, previousRound[i+1].player, ""))
                    j++
                    i += 2
                }
            }
            else{
                var tempList = (previousRound).sortedWith(compareBy({it.wins}, {it.player}))
                var i = 0
                var j = 0
                while (i < tempList.size) {
                    currentRound.add(j, Game(tempList[i].player, tempList[i+1].player, ""))
                    j++
                    i += 2
                }
                previousRound = ArrayList(tempList)
            }

        }
        else{

            previousRound.shuffle()

            var i = 0
            var j = 0
            while (i < previousRound.size) {
                currentRound.add(j, Game(previousRound[i].player, previousRound[i+1].player, ""))
                j++
                i += 2
            }
        }
        for(i in 0.until(currentRound.size)){
            if (currentRound[i].name1.length >= 3 && currentRound[i].name1.subSequence(0, 3) == "BYE") {
                currentRound[i].winner = currentRound[i].name2
            }
            if (currentRound[i].name2.length >=3 && currentRound[i].name2.subSequence(0, 3) == "BYE") {
                currentRound[i].winner = currentRound[i].name1
            }
        }

        createRecyclerView(currentRound)
    }

    private fun createRecyclerView(list: ArrayList<Game>){
        val rv = findViewById<RecyclerView>(R.id.recyclerViewBracket)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var adapter = SingleElimAdapter(list, false)
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
        var numBye = 0
        if(roundNumber == 1 && previousRound.size % 2 == 1){
            numBye++
            previousRound.add(PreLimData("BYE#" + numBye.toString(), 0))
        }

        createOpponents()

        //Need to fix issue with not showing correctly some stuff with going back and creating another instead of getting right activity.
        previous_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
        next_button.setOnClickListener{
            //Go to single elimination with the correct number of wins
            if(roundNumber == totalRounds){

                //Update List
                nextRound = previousRound
                for(i in 0.until(currentRound.size)){
                    for(j in 0.until(nextRound.size)){
                        if(currentRound[i].winner == nextRound[j].player){
                            nextRound[j].wins++
                            break
                        }
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
                var allRecorded = true
                for(i in 0.until(currentRound.size)){
                    if(currentRound[i].winner == ""){
                        allRecorded = false
                        break
                    }
                }

                if(!allRecorded){
                    Toast.makeText(this, "Not all rounds have been recorded!", Toast.LENGTH_LONG).show()
                }
                else{
                    //Update List
                    nextRound = previousRound
                    for(i in 0.until(currentRound.size)){
                        for(j in 0.until(nextRound.size)){
                            if(currentRound[i].winner == nextRound[j].player){
                                nextRound[j].wins++
                                break
                            }
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
    }
}