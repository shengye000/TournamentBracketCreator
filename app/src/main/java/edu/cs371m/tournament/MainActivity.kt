package edu.cs371m.tournament

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        single_elim_button.setOnClickListener {
            val intent = Intent(this, ElimActivity::class.java)
            intent.putExtra("type", "s")
            startActivity(intent)
        }

        double_elim_button.setOnClickListener{
            val intent = Intent(this, ElimActivity::class.java)
            intent.putExtra("type", "d")
            startActivity(intent)
        }

        round_robin_button.setOnClickListener {
            val intent = Intent(this, ElimActivity::class.java)
            intent.putExtra("type", "rr")
            startActivity(intent)
        }

        pre_elim_button.setOnClickListener {
            val intent = Intent(this, ElimActivity::class.java)
            intent.putExtra("type", "pre")
            startActivity(intent)
        }
    }
}
