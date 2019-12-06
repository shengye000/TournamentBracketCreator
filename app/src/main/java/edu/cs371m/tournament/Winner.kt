package edu.cs371m.tournament

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.winner_page.*
import android.content.Intent
import android.graphics.Color
import android.widget.ImageView
import com.bumptech.glide.Glide
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


class Winner : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.winner_page)

        //Add gif of celebration
        val iView = findViewById<ImageView>(R.id.test_image)
        if (iView != null)
            Glide.with(this).load("https://media.giphy.com/media/3Gm15eZOsNk0tptIuG/source.gif").into(iView)

        //Add confetti
        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.RED)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2500L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes( Size(12, 5.toFloat()))
            .setPosition(-1000f, viewKonfetti.width + 1000f, -1000f, 1000f)
            .streamFor(300, 100000L)
        viewKonfetti2.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.RED)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2500L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes( Size(12, 5.toFloat()))
            .setPosition(-1000f, viewKonfetti.width + 1000f, -1000f, 1000f)
            .streamFor(300, 100000L)

        val winner = intent.getStringExtra("winner")
        winner_name.text = winner.toString()

        //Close all activities instead of main
        reset_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }
}