package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.tournament.api.ChallongeInfo
import edu.cs371m.tournament.api.CreateInfo2
import edu.cs371m.tournament.api.CreateParticipants
import kotlinx.android.synthetic.main.challonge_view.*

class Challonge : AppCompatActivity(){
    private lateinit var viewModel: ChallongeViewModel
    private lateinit var list : List<ChallongeInfo>
    private lateinit var currentList  : MutableList<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private fun refreshList(){
        viewModel.observeChallongeInfo().observe(this, Observer{
            list = it
            if(list.size >= 0){
                currentList = mutableListOf()
                currentList.clear()
                for(i in 0.until(list.size)){
                    currentList.add(list[i].data.name)
                }

                //adapter
                viewManager = LinearLayoutManager(this)
                viewAdapter = ElimActivityAdapter(currentList){
                    edit_but.setText(it)
                }

                recyclerView = findViewById<RecyclerView>(R.id.recyclerViewChallonge).apply {
                    layoutManager = viewManager
                    adapter = viewAdapter
                }


            }

        })
        viewModel.netRefreshChallonge()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.challonge_view)

        //Toolbar functionality
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            MainActivity.initActionBar(it, this)
        }

        viewModel = ViewModelProviders.of(this)[ChallongeViewModel::class.java]
        viewModel.chosenTournament(intent.getStringExtra("url_string"))
        viewModel.chosenapiKey(intent.getStringExtra("api_string"))
        Log.d("debug", "In challonge" + viewModel.returnURL())

        currentList = mutableListOf()
        refreshList()

        add_but.setOnClickListener {
            val text = edit_but.text
            if(text.isEmpty()){
                Toast.makeText(this, "Text Box is empty.", Toast.LENGTH_LONG).show()
            }
            else if(currentList.contains(text.toString())){
                Toast.makeText(this, "This name has already been taken. Please select another.", Toast.LENGTH_LONG).show()
            }
            else{
                viewModel.netRefreshCreateParticipant(text.toString(), 1)
                viewModel.observeCreateParticipantInfo().observe(this, Observer{
                    refreshList()
                })
            }
            edit_but.text.clear()
            MainActivity.hideKeyboardActivity(this@Challonge)
        }
        del_but.setOnClickListener {
            val text = edit_but.text
            if(text.isEmpty()){
                Toast.makeText(this, "Text Box is empty.", Toast.LENGTH_LONG).show()
            }

            if(currentList.contains(text.toString())){
                var id = 0
                for(item in list){
                    if(item.data.name == text.toString()){
                        id = item.data.id
                        break
                    }
                }
                viewModel.deleteParticipantInfo(viewModel.returnURL(), id)
                Thread.sleep(1000)
                refreshList()
            }
            else{
                Toast.makeText(this, "This name is not currently in the list.", Toast.LENGTH_LONG).show()
            }
            edit_but.text.clear()
            MainActivity.hideKeyboardActivity(this@Challonge)
        }
        add_all_but.setOnClickListener {
            val text = edit_but.text
            if(text.isEmpty()){
                Toast.makeText(this, "Text Box is empty.", Toast.LENGTH_LONG).show()
            }
            else{
                var result: List<String> = text.toString().split(",").map { it.trim() }
                var canAdd = true
                Log.d("result", result.toString())
                //check if duplicates exist in the list.
                result.forEach{
                    if(currentList.contains(it)){
                        Toast.makeText(this, it + " is a duplicate in the list. Cannot add.", Toast.LENGTH_SHORT).show()
                        canAdd = false
                    }
                }
                //if able to be added to the list
                if(canAdd){
                    //do the add
                    viewModel.bulkAddParticipantInfo(viewModel.returnURL(), ArrayList(result))
                    Thread.sleep(1000)
                    refreshList()
                }
            }

        }
        del_all_but.setOnClickListener {
            viewModel.deleteAllParticipantInfo(viewModel.returnURL())
            Thread.sleep(1000)
            refreshList()
        }
        shuffle_but.setOnClickListener {
            viewModel.randomizeInfo(viewModel.returnURL())
            Thread.sleep(1000)
            refreshList()
        }

        //TODO: Set up 4 competitor requirement
        single_button.setOnClickListener {
            val intent = Intent(this, SingleElim::class.java)
            intent.putExtra("list", ArrayList(currentList))
            intent.putExtra("round", 1)
            intent.putExtra("result", ArrayList<List<Game>>())
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        double_button.setOnClickListener {
            val intent = Intent(this, DoubleElim::class.java)
            val loserList = ArrayList<String>()
            intent.putExtra("winner_list", ArrayList(currentList))
            intent.putExtra("loser_list", loserList)
            intent.putExtra("round", 1)
            startActivity(intent)
        }
        rr_button.setOnClickListener {
            val intent = Intent(this, RoundRobin::class.java)
            intent.putExtra("list", ArrayList(currentList))
            intent.putExtra("round", 1)
            startActivity(intent)
        }
        prelim_button.setOnClickListener {
            val intent = Intent(this, PreLim::class.java)
            intent.putExtra("list", ArrayList(currentList))
            startActivity(intent)
        }

    }
}