package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.double_view_grand_finals.*

class DoubleElimGrandFinals : AppCompatActivity(){

    private lateinit var winnerName: String
    private lateinit var loserName: String
    private var round = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.double_view_grand_finals)

        round = intent.getIntExtra("round", 0)

        if(round == 1){
            winnerName = intent.getStringExtra("winner")
            loserName = intent.getStringExtra("loser")
            winner.text = winnerName + " [W]"
            loser.text = loserName + " [L]"
            round_num.text = "ROUND 1"
            winner_button.setOnClickListener {
                val intent = Intent(this, Winner::class.java)
                intent.putExtra("winner", winnerName)
                startActivity(intent)
            }
            loser_button.setOnClickListener {
                val intent = Intent(this, DoubleElimGrandFinals::class.java)
                intent.putExtra("winner",winnerName )
                intent.putExtra("loser", loserName)
                intent.putExtra("round", 2)
                startActivity(intent)
            }
        }
        if(round == 2) {
            winnerName = intent.getStringExtra("winner")
            loserName = intent.getStringExtra("loser")
            winner.text = winnerName + " [L]"
            loser.text = loserName + " [L]"
            round_num.text = "RESET"
            winner_button.setOnClickListener {
                val intent = Intent(this, Winner::class.java)
                intent.putExtra("winner", winnerName)
                startActivity(intent)
            }
            loser_button.setOnClickListener {
                val intent = Intent(this, Winner::class.java)
                intent.putExtra("winner", loserName)
                startActivity(intent)
            }
        }
    }
}