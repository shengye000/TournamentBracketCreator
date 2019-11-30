package edu.cs371m.tournament

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.prelim_settings.*
import java.io.Serializable


class PreLim : AppCompatActivity(), Serializable{

    private lateinit var competitorList : ArrayList<String>
    private lateinit var competitorListWins : ArrayList<PreLimData>

    private fun createBracket(){

        if(!number_of_rounds.text.isNullOrEmpty()
            && number_of_rounds.text.toString().toInt() >= 1
            && number_of_rounds.text.toString().toInt() <= 6
            && number_of_rounds.text.toString().toInt() <= competitorList.size/2)
        {

            var ques1 = number_of_rounds.text.toString().toInt()
            if(rounds_to_proceed.text.isNotEmpty() && rounds_to_proceed.text.toString().toInt() <= ques1){
                var ques2 = rounds_to_proceed.text.toString().toInt()

                //Activity
                val intent = Intent(this, PreLim2::class.java)
                intent.putExtra("list", competitorListWins)
                intent.putExtra("total", ques1)
                intent.putExtra("needed", ques2)
                if(win_loss_ratio.isChecked){
                    var ques3 = true
                    intent.putExtra("weighed" , ques3)
                }
                else{
                    var ques3 = false
                    intent.putExtra("weighed", ques3)
                }
                intent.putExtra("round", 1)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Invalid response for Question 2", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this, "Invalid response for Question 1", Toast.LENGTH_LONG).show()

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prelim_settings)

        competitorList = intent.getStringArrayListExtra("list")
        competitorListWins = ArrayList()
        for(i in competitorList.indices){
            competitorListWins.add(i,PreLimData(competitorList[i], 0))
        }

        create_bracket.setOnClickListener {
            createBracket()
        }

    }
}