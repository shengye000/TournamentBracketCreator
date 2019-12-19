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
import kotlinx.android.synthetic.main.settings_page_1.*

class ElimActivity : AppCompatActivity() {

    private var bracketType = ""
    private var anonNum = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var mutableList = mutableListOf<String>()
    private var filteredList = listOf<String>()

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
                    filteredList = mutableList.filter{s -> (s.contains(p0, true))}
                    recyclerViewList(filteredList.toMutableList())
                }
            }
        })
    }


    private fun recyclerViewList(list: MutableList<String>){
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

    private fun cleanUp(){
        userET.text.clear()
        viewAdapter.notifyDataSetChanged()
        val titleBar = findViewById<EditText>(R.id.actionSearch)
        titleBar.text.clear()
        recyclerViewList(mutableList)
        bracket_desc.text="Write the competitor's name and click Add or Delete. \nNumber of competitors: " + mutableList.size.toString()
    }

    private fun createBracket(){
        recyclerViewList(mutableList)

        add_button.setOnClickListener {
            val text = userET.text
            MainActivity.hideKeyboardActivity(this)
            if(text.isEmpty()){
                Toast.makeText(this, "Text Box is empty.", Toast.LENGTH_LONG).show()
            }
            else if((text.length >= 3 && text.subSequence(0,3).toString() == "BYE") || (text.length >= 6 && text.subSequence(0,6).toString() == "PLAYER")){
                Toast.makeText(this, "You cannot add names containing BYE or PLAYER. Please select another.", Toast.LENGTH_LONG).show()
                text.clear()
            }
            else if(mutableList.contains(text.toString())){
                Toast.makeText(this, "This name has already been taken. Please select another.", Toast.LENGTH_LONG).show()
                text.clear()
            }
            else{
                mutableList.add(text.toString())
                cleanUp()
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
                cleanUp()
            }
            else{
                Toast.makeText(this, "This name is not currently in the list.", Toast.LENGTH_LONG).show()
                text.clear()
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
                   mutableList.add("PLAYER" + anonNum)
                   anonNum++
                   cleanUp()
               }
            }
        }
        create_button.setOnClickListener{
            if(mutableList.size < 4 || mutableList.size > 256){
                Toast.makeText(this, "The minimum competitor size is 4 and the maximum size is 256.", Toast.LENGTH_LONG).show()
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
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        if(bracketType == "d"){
            val intent = Intent(this, DoubleElim::class.java)
            val loserList = ArrayList<String>()
            intent.putExtra("winner_list", ArrayList(mutableList))
            intent.putExtra("loser_list", loserList)
            intent.putExtra("round", 1)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        if(bracketType == "rr"){
            val intent = Intent(this, RoundRobin::class.java)
            intent.putExtra("list", ArrayList(mutableList))
            intent.putExtra("round", 1)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        if(bracketType == "pre"){
            val intent = Intent(this, PreLim::class.java)
            intent.putExtra("list", ArrayList(mutableList))
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        finish()
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
            bracket_type_title.text = "Single Elimination"
        }
        if(bracketType == "d"){
            bracket_type_title.text = "Double Elimination"
        }
        if(bracketType == "rr"){
            bracket_type_title.text = "Round Robin"
        }
        if(bracketType == "pre"){
            bracket_type_title.text = "Pre Elimination"
        }
        bracket_desc.text="Write the competitor's name and click Add or Delete. \nNumber of competitors: " + mutableList.size.toString()

        createBracket()
    }
}