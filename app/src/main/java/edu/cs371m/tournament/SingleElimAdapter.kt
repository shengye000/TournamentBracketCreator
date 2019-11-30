package edu.cs371m.tournament

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class SingleElimAdapter(val userList: ArrayList<Game>, val listener: (String) -> Unit): RecyclerView.Adapter<SingleElimAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName1?.text = userList[position].name1
        holder?.txtName2?.text = userList[position].name2

        holder?.number?.text = (position + 1).toString()

        holder?.txtButton1?.setOnClickListener {
            holder?.txtButton1?.text = "W"
            holder?.txtButton1?.setBackgroundColor(Color.GREEN)
            holder?.txtButton2?.text = "L"
            holder?.txtButton2?.setBackgroundColor(Color.RED)
            listener(holder?.txtName1?.text.toString())
            holder?.txtButton1?.isClickable = false
            holder?.txtButton2?.isClickable = false
        }
        holder?.txtButton2?.setOnClickListener{
            holder?.txtButton2?.text = "W"
            holder?.txtButton2?.setBackgroundColor(Color.GREEN)
            holder?.txtButton1?.text = "L"
            holder?.txtButton1?.setBackgroundColor(Color.RED)
            listener(holder?.txtName2?.text.toString())
            holder?.txtButton1?.isClickable = false
            holder?.txtButton2?.isClickable = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_row, parent, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName1 = itemView.findViewById<TextView>(R.id.player_1)
        val txtName2 = itemView.findViewById<TextView>(R.id.player_2)

        val txtButton1 = itemView.findViewById<Button>(R.id.player_1_button)
        val txtButton2 = itemView.findViewById<Button>(R.id.player_2_button)

        val number = itemView.findViewById<TextView>(R.id.bracket_number)
    }

}