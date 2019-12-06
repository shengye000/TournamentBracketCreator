package edu.cs371m.tournament

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri


class MainActivity : AppCompatActivity() {

    //Challonge API key:R3Xxr5i4HvMOKLIaito1hOtArNGVEEYilmxPj4Ts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val iView = findViewById<ImageView>(R.id.home_image)
        if (iView != null)
            Glide.with(this).load("https://static.wixstatic.com/media/178c4e_8f0e61a9fbc94eb08e61a4f72eb106d0~mv2.gif").centerCrop().into(iView)
        iView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://tonyspasta.com/"))
            startActivity(browserIntent)
        }

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
        challonge_button.setOnClickListener {
            val intent = Intent(this, Tournament::class.java)
            startActivity(intent)
        }
    }
}
