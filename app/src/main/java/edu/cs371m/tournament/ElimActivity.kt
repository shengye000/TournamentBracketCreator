package edu.cs371m.tournament

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.settings_page_1.*

class ElimActivity : AppCompatActivity() {

    private var bracketType = ""
    private var anonNum = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var mutableList = mutableListOf<String>()

    private fun titleSearch() {
        val titleBar = findViewById<EditText>(R.id.actionSearch)
        titleBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isEmpty()){
                    MainActivity.hideKeyboardActivity(this@ElimActivity)
                    recyclerViewList(mutableList.toMutableList())
                }
                else{
                    var filteredList = mutableList.filter{s -> (s.contains(p0, true))}
                    recyclerViewList(filteredList.toMutableList())
                }
            }
        })
    }

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

    fun recyclerViewList(list: MutableList<String>){
        viewManager = LinearLayoutManager(this)
        viewAdapter = ElimActivityAdapter(list) {
            userET.setText(it)
        }
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewList).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    fun createBracket(){
        recyclerViewList(mutableList)

        add_button.setOnClickListener {
            val text = userET.text
            MainActivity.hideKeyboardActivity(this)
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
                recyclerViewList(mutableList)
            }
        }
        delete_button.setOnClickListener {
            val text = userET.text
            MainActivity.hideKeyboardActivity(this)
            if(text.isEmpty()){
                Toast.makeText(this, "Text Box is empty.", Toast.LENGTH_LONG).show()
            }

            if(mutableList.contains(text.toString())){
                mutableList.remove(text.toString())
                userET.text.clear()
                viewAdapter.notifyDataSetChanged()
                recyclerViewList(mutableList)
            }
            else{
                Toast.makeText(this, "This name is not currently in the list.", Toast.LENGTH_LONG).show()
            }
        }
        add_all_button.setOnClickListener{
            val text = userET2.text
            MainActivity.hideKeyboardActivity(this)
            if(text.isNullOrEmpty()){
                Toast.makeText(this, "Text Box is empty.", Toast.LENGTH_LONG).show()
            }
            else{
               for(i in 0.until(text.toString().toInt())){
                   mutableList.add("Player " + anonNum)
                   anonNum++
                   userET2.text.clear()
                   viewAdapter.notifyDataSetChanged()
                   recyclerViewList(mutableList)
               }
            }
        }
        create_button.setOnClickListener{
            if(mutableList.size < 4){
                Toast.makeText(this, "You must have 4 or more participants to create the bracket", Toast.LENGTH_LONG).show()
            }
            else{
                bracketActivity()
            }
        }
    }

    private fun bracketActivity(){
        if(bracketType == "s"){
            val intent = Intent(this, SingleElim::class.java)
            intent.putExtra("list", ArrayList(mutableList))
            intent.putExtra("round", 1)
            startActivity(intent)
        }
        if(bracketType == "d"){
            val intent = Intent(this, DoubleElim::class.java)
            val loserList = ArrayList<String>()
            intent.putExtra("winner_list", ArrayList(mutableList))
            intent.putExtra("loser_list", loserList)
            intent.putExtra("round", 1)
            startActivity(intent)
        }
        if(bracketType == "rr"){
            val intent = Intent(this, RoundRobin::class.java)
            intent.putExtra("list", ArrayList(mutableList))
            intent.putExtra("round", 1)
            startActivity(intent)
        }
        if(bracketType == "pre"){
            val intent = Intent(this, PreLim::class.java)
            intent.putExtra("list", ArrayList(mutableList))
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page_1)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            MainActivity.initActionBar(it, this)
        }

        titleSearch()

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