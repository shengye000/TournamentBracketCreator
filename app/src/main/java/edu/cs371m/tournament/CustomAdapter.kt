package edu.cs371m.tournament

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ViewHolder(inflater: LayoutInflater, parent: ViewGroup)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.recyclerview_row, parent, false)){
    private var viewPlayer1 : TextView? = null
    private var viewPlayer1Score : TextView? = null
    private var viewPlayer2 : TextView? = null
    private var viewPlayer2Score : TextView? = null

    init{
        //viewPlayer1 = itemView.findViewById(R.id.top_player)
//        viewPlayer1Score = itemView.findViewById(R.id.top_player_score)
//        viewPlayer2 = itemView.findViewById(R.id.bottom_player)
//        viewPlayer2Score = itemView.findViewById(R.id.bottom_player_score)
    }

    fun bind(bracket: HomeFragment.Bracket){
//        viewPlayer1?.text = bracket.p1
//        viewPlayer1Score?.text = bracket.score1.toString()
//        viewPlayer2?.text = bracket.p2
//        viewPlayer2Score?.text = bracket.score2.toString()

    }

}