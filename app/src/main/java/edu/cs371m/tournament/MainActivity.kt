package edu.cs371m.tournament

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri
import android.app.Activity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {

    object hideKeyboard{
        fun hideKeyboardActivity(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val iView = findViewById<ImageView>(R.id.home_image)
        if (iView != null)
            Glide.with(this).load("https://static.wixstatic.com/media/178c4e_8f0e61a9fbc94eb08e61a4f72eb106d0~mv2.gif").fitCenter().into(iView)
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
