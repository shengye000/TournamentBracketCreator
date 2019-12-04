package edu.cs371m.tournament

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.tournament.api.TournamentInfo
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class TournamentAdapter(val userList: List<TournamentInfo>, val listener: (String) -> Unit): RecyclerView.Adapter<TournamentAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.txtName1?.text = userList[position].data2.name2
        holder?.txtName2?.text = "# of competitors: " + userList[position].data2.participant2.toString()
        holder?.txtButton1.setOnClickListener {
            listener(userList[position].data2.url2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_tournament, parent, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName1 = itemView.findViewById<TextView>(R.id.tournament_name)
        val txtName2 = itemView.findViewById<TextView>(R.id.tournament_number)
        val txtButton1 = itemView.findViewById<Button>(R.id.button_next)
    }

}