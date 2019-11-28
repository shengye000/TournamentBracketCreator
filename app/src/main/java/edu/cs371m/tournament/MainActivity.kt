package edu.cs371m.tournament

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import edu.cs371m.tournament.HomeFragment
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment

    private fun initHomeFragment() {
        supportFragmentManager
            .beginTransaction()
            // No back stack for home
            .add(R.id.main_frame, homeFragment, "HomeFragment") //added tag
            // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }


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
