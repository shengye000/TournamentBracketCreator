package edu.cs371m.tournament

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.challonge_view.*

class Challonge : AppCompatActivity(){
    private lateinit var viewModel: ChallongeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.challonge_view)

        viewModel = ViewModelProviders.of(this)[ChallongeViewModel::class.java]
        viewModel.observeChallongeInfo().observe(this, Observer{
            challonge_test.text = it.toString()
        })
        viewModel.netRefresh()
    }
}