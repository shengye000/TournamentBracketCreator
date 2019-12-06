package edu.cs371m.tournament

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class RoundRobinTieAdapter(val userList: ArrayList<RoundRobinTieBreakerData>, val listener: (String) -> Unit): RecyclerView.Adapter<RoundRobinTieAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName1?.text = "Question " + (position + 1).toString()

        holder?.txtButton1?.text = userList[position].s1
        holder?.txtButton2?.text = userList[position].s2
        holder?.txtButton3?.text = userList[position].s3

        if(holder?.txtButton3?.text == "!null"){
            holder?.txtButton3?.isClickable = false
            holder?.txtButton3?.isVisible = false
        }
        else{
            holder?.txtButton3?.setOnClickListener {
                holder?.txtButton1?.setBackgroundColor(Color.RED)
                holder?.txtButton2?.setBackgroundColor(Color.RED)
                holder?.txtButton1?.isClickable = false
                holder?.txtButton2?.isClickable = false
                holder?.txtButton3?.setBackgroundColor(Color.GREEN)
                holder?.txtButton3?.isClickable = false
                listener(holder?.txtButton3?.text.toString())
            }
        }
        holder?.txtButton1?.setOnClickListener {
            holder?.txtButton1?.setBackgroundColor(Color.GREEN)
            holder?.txtButton2?.setBackgroundColor(Color.RED)
            holder?.txtButton1?.isClickable = false
            holder?.txtButton2?.isClickable = false
            if(holder?.txtButton3?.text != "!null"){
                holder?.txtButton3?.setBackgroundColor(Color.RED)
                holder?.txtButton3?.isClickable = false
            }
            listener(holder?.txtButton1?.text.toString())
        }
        holder?.txtButton2?.setOnClickListener{
            holder?.txtButton2?.setBackgroundColor(Color.GREEN)
            holder?.txtButton1?.setBackgroundColor(Color.RED)
            holder?.txtButton1?.isClickable = false
            holder?.txtButton2?.isClickable = false
            if(holder?.txtButton3?.text != "!null"){
                holder?.txtButton3?.setBackgroundColor(Color.RED)
                holder?.txtButton3?.isClickable = false
            }
            listener(holder?.txtButton2?.text.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.round_robin_tie, parent, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName1 = itemView.findViewById<TextView>(R.id.tie_round_number)

        val txtButton1 = itemView.findViewById<Button>(R.id.tie_button_1)
        val txtButton2 = itemView.findViewById<Button>(R.id.tie_button_2)
        val txtButton3 = itemView.findViewById<Button>(R.id.tie_button_3)
    }

}