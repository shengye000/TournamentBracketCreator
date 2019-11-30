package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.settings_page_1.*

class ElimActivity : AppCompatActivity() {

    private var bracketType = ""
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var mutableList = mutableListOf<String>()

    fun singleBracket(){
        bracket_type_title.text = "Single Elimination"
        bracket_desc.text="Write the competitor name and Click Add or Remove."
    }

    fun doubleBracket(){
        bracket_type_title.text = "Double Elimination"
        bracket_desc.text="Write the competitor name and Click Add or Remove."
    }

    fun roundRobinBracket(){
        bracket_type_title.text = "Round Robin"
        bracket_desc.text="Write the competitor name and Click Add or Remove."
    }

    fun prelimBracket(){
        bracket_type_title.text = "Pre Elimination"
        bracket_desc.text="Write the competitor name and Click Add or Remove."
    }

    fun createBracket(){
        viewManager = LinearLayoutManager(this)
        viewAdapter = ElimActivityAdapter(mutableList)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewList).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        add_button.setOnClickListener {
            val text = userET.text
            if(text.isEmpty()){
                Toast.makeText(this, "Text Box is empty.", Toast.LENGTH_LONG).show()
            }
            else if(mutableList.contains(text.toString())){
                Toast.makeText(this, "This name has already been taken. Please select another.", Toast.LENGTH_LONG).show()
            }
            else{
                mutableList.add(text.toString())
                userET.text.clear()
                viewAdapter.notifyDataSetChanged()
            }
        }
        delete_button.setOnClickListener {
            val text = userET.text
            if(text.isEmpty()){
                Toast.makeText(this, "Text Box is empty.", Toast.LENGTH_LONG).show()
            }

            if(mutableList.contains(text.toString())){
                mutableList.remove(text.toString())
                userET.text.clear()
                viewAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(this, "This name is not currently in the list.", Toast.LENGTH_LONG).show()
            }
        }
        create_button.setOnClickListener{
            if(mutableList.size < 2){
                Toast.makeText(this, "You must have 2 or more participants to create the bracket", Toast.LENGTH_LONG).show()
            }
            else{
                bracketActivity()
            }
        }
    }

    fun bracketActivity(){
        if(bracketType == "s"){
            val intent = Intent(this, SingleElim::class.java)
            intent.putExtra("list", ArrayList(mutableList))
            intent.putExtra("round", 1)
            startActivity(intent)
        }
        if(bracketType == "d"){

        }
        if(bracketType == "rr"){

        }
        if(bracketType == "pre"){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page_1)

        bracketType = intent.getStringExtra("type")
        Log.d("Bracket", bracketType)
        if(bracketType == "s"){
            singleBracket()
        }
        if(bracketType == "d"){
            doubleBracket()
        }
        if(bracketType == "rr"){
            roundRobinBracket()
        }
        if(bracketType == "pre"){
            prelimBracket()
        }

        createBracket()
    }
}