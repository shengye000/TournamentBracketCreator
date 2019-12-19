package edu.cs371m.tournament

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.net.Uri
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar





class MainActivity : AppCompatActivity() {

    companion object MainActivityFunctions{
        fun hideKeyboardActivity(activity: Activity) : Boolean{
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }

        fun initActionBar(actionBar: ActionBar, activity: Activity) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayShowCustomEnabled(true)
            val customView: View =
                activity.layoutInflater.inflate(R.layout.action_bar, null)
            // Apply the custom view
            actionBar.customView = customView

            val titleBar = activity.findViewById<Button>(R.id.searchButton)
            titleBar.setOnClickListener {
                val intent = Intent(activity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                activity.startActivity(intent)
            }

            val actionSearch = activity.findViewById<EditText>(R.id.actionSearch)
            actionSearch.setOnLongClickListener {
                hideKeyboardActivity(activity)
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            initActionBar(it, this)
        }

        val iView = findViewById<ImageView>(R.id.home_image)
        if (iView != null)
            Glide.with(this).load("https://static.wixstatic.com/media/178c4e_8f0e61a9fbc94eb08e61a4f72eb106d0~mv2.gif").fitCenter().into(iView)
        iView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://tonyspasta.com/"))
            startActivity(browserIntent)
        }
        val view = findViewById<View>(R.id.content_xml)

        view.findViewById<Button>(R.id.single_elim_button).setOnClickListener {
            val intent = Intent(this, ElimActivity::class.java)
            intent.putExtra("type", "s")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.double_elim_button).setOnClickListener{
            val intent = Intent(this, ElimActivity::class.java)
            intent.putExtra("type", "d")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.round_robin_button).setOnClickListener {
            val intent = Intent(this, ElimActivity::class.java)
            intent.putExtra("type", "rr")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.pre_elim_button).setOnClickListener {
            val intent = Intent(this, ElimActivity::class.java)
            intent.putExtra("type", "pre")
            startActivity(intent)
        }
        view.findViewById<Button>(R.id.challonge_button).setOnClickListener {
            val intent = Intent(this, Tournament::class.java)
            startActivity(intent)
        }
    }
}
