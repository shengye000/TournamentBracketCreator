package edu.cs371m.tournament

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class RoundRobinAdapter(private val userList: ArrayList<RoundRobinGame>): RecyclerView.Adapter<RoundRobinAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName1Top?.text = userList[position].p1
        holder?.txtName1?.text = userList[position].p1
        holder?.txtName2Top?.text = userList[position].p2
        holder?.txtName2?.text = userList[position].p2
        holder?.txtName3Top?.text = userList[position].p3
        holder?.txtName3?.text = userList[position].p3
        holder?.txtName4Top?.text = userList[position].p4
        holder?.txtName4?.text = userList[position].p4

        holder?.number?.text = "Bracket #" + (position + 1).toString()

        if(holder.adapterPosition == position && userList[position].edited){
            if(userList[position].p12 == 0){
                holder.txtButton12.text = "L"
                holder.txtButton12.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p12 == 1){
                holder.txtButton12.text = "W"
                holder.txtButton12.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p12 == -1){
                holder.txtButton12.text = ""
                holder.txtButton12.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p13 == 0){
                holder.txtButton13.text = "L"
                holder.txtButton13.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p13 == 1){
                holder.txtButton13.text = "W"
                holder.txtButton13.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p13 == -1){
                holder.txtButton13.text = ""
                holder.txtButton13.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p14 == 0){
                holder.txtButton14.text = "L"
                holder.txtButton14.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p14 == 1){
                holder.txtButton14.text = "W"
                holder.txtButton14.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p14 == -1){
                holder.txtButton14.text = ""
                holder.txtButton14.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p21 == 0){
                holder.txtButton21.text = "L"
                holder.txtButton21.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p21 == 1){
                holder.txtButton21.text = "W"
                holder.txtButton21.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p21 == -1){
                holder.txtButton21.text = ""
                holder.txtButton21.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p23 == 0){
                holder.txtButton23.text = "L"
                holder.txtButton23.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p23 == 1){
                holder.txtButton23.text = "W"
                holder.txtButton23.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p23 == -1){
                holder.txtButton23.text = ""
                holder.txtButton23.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p24 == 0){
                holder.txtButton24.text = "L"
                holder.txtButton24.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p24 == 1){
                holder.txtButton24.text = "W"
                holder.txtButton24.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p24 == -1){
                holder.txtButton24.text = ""
                holder.txtButton24.setBackgroundResource(R.drawable.buttonneutral)
            }


            if(userList[position].p31 == 0){
                holder.txtButton31.text = "L"
                holder.txtButton31.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p31 == 1){
                holder.txtButton31.text = "W"
                holder.txtButton31.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p31 == -1){
                holder.txtButton31.text = ""
                holder.txtButton31.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p32 == 0){
                holder.txtButton32.text = "L"
                holder.txtButton32.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p32 == 1){
                holder.txtButton32.text = "W"
                holder.txtButton32.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p32 == -1){
                holder.txtButton32.text = ""
                holder.txtButton32.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p34 == 0){
                holder.txtButton34.text = "L"
                holder.txtButton34.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p34 == 1){
                holder.txtButton34.text = "W"
                holder.txtButton34.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p34 == -1){
                holder.txtButton34.text = ""
                holder.txtButton34.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p41 == 0){
                holder.txtButton41.text = "L"
                holder.txtButton41.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p41 == 1){
                holder.txtButton41.text = "W"
                holder.txtButton41.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p41 == -1){
                holder.txtButton41.text = ""
                holder.txtButton41.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p42 == 0){
                holder.txtButton42.text = "L"
                holder.txtButton42.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p42 == 1){
                holder.txtButton42.text = "W"
                holder.txtButton42.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p42 == -1){
                holder.txtButton42.text = ""
                holder.txtButton42.setBackgroundResource(R.drawable.buttonneutral)
            }

            if(userList[position].p43 == 0){
                holder.txtButton43.text = "L"
                holder.txtButton43.setBackgroundResource(R.drawable.buttonloser)
            }
            if(userList[position].p43 == 1){
                holder.txtButton43.text = "W"
                holder.txtButton43.setBackgroundResource(R.drawable.buttonwinner)
            }
            if(userList[position].p43 == -1){
                holder.txtButton43.text = ""
                holder.txtButton43.setBackgroundResource(R.drawable.buttonneutral)
            }
        }
        else{
            holder.txtButton12.text = ""
            holder.txtButton12.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton13.text = ""
            holder.txtButton13.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton14.text = ""
            holder.txtButton14.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton21.text = ""
            holder.txtButton21.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton23.text = ""
            holder.txtButton23.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton24.text = ""
            holder.txtButton24.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton31.text = ""
            holder.txtButton31.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton32.text = ""
            holder.txtButton32.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton34.text = ""
            holder.txtButton34.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton41.text = ""
            holder.txtButton41.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton42.text = ""
            holder.txtButton42.setBackgroundResource(R.drawable.buttonneutral)
            holder.txtButton43.text = ""
            holder.txtButton43.setBackgroundResource(R.drawable.buttonneutral)
        }

        //Click Listeners
        holder.txtButton12.setOnClickListener {
            userList[position].edited = true
            userList[position].p12 = 1
            userList[position].p21 = 0
            holder.txtButton12.text = "W"
            holder.txtButton12.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton21.text = "L"
            holder.txtButton21.setBackgroundResource(R.drawable.buttonloser)
        }
        holder.txtButton13.setOnClickListener {
            userList[position].edited = true
            userList[position].p13 = 1
            userList[position].p31 = 0
            holder.txtButton13.text = "W"
            holder.txtButton13.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton31.text = "L"
            holder.txtButton31.setBackgroundResource(R.drawable.buttonloser)
        }
        holder.txtButton14.setOnClickListener {
            userList[position].edited = true
            userList[position].p14 = 1
            userList[position].p41 = 0
            holder.txtButton14.text = "W"
            holder.txtButton14.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton41.text = "L"
            holder.txtButton41.setBackgroundResource(R.drawable.buttonloser)
        }

        holder.txtButton21.setOnClickListener {
            userList[position].edited = true
            userList[position].p21 = 1
            userList[position].p12 = 0
            holder.txtButton21.text = "W"
            holder.txtButton21.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton12.text = "L"
            holder.txtButton12.setBackgroundResource(R.drawable.buttonloser)
        }
        holder.txtButton23.setOnClickListener {
            userList[position].edited = true
            userList[position].p23 = 1
            userList[position].p32 = 0
            holder.txtButton23.text = "W"
            holder.txtButton23.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton32.text = "L"
            holder.txtButton32.setBackgroundResource(R.drawable.buttonloser)
        }
        holder.txtButton24.setOnClickListener {
            userList[position].edited = true
            userList[position].p24 = 1
            userList[position].p42 = 0
            holder.txtButton24.text = "W"
            holder.txtButton24.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton42.text = "L"
            holder.txtButton42.setBackgroundResource(R.drawable.buttonloser)
        }

        holder.txtButton31.setOnClickListener {
            userList[position].edited = true
            userList[position].p31 = 1
            userList[position].p13 = 0
            holder.txtButton31.text = "W"
            holder.txtButton31.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton13.text = "L"
            holder.txtButton13.setBackgroundResource(R.drawable.buttonloser)
        }
        holder.txtButton32.setOnClickListener {
            userList[position].edited = true
            userList[position].p32 = 1
            userList[position].p23 = 0
            holder.txtButton32.text = "W"
            holder.txtButton32.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton23.text = "L"
            holder.txtButton23.setBackgroundResource(R.drawable.buttonloser)
        }
        holder.txtButton34.setOnClickListener {
            userList[position].edited = true
            userList[position].p34 = 1
            userList[position].p43 = 0
            holder.txtButton34.text = "W"
            holder.txtButton34.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton43.text = "L"
            holder.txtButton43.setBackgroundResource(R.drawable.buttonloser)
        }

        holder.txtButton41.setOnClickListener {
            userList[position].edited = true
            userList[position].p41 = 1
            userList[position].p14 = 0
            holder.txtButton41.text = "W"
            holder.txtButton41.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton14.text = "L"
            holder.txtButton14.setBackgroundResource(R.drawable.buttonloser)
        }
        holder.txtButton42.setOnClickListener {
            userList[position].edited = true
            userList[position].p42 = 1
            userList[position].p24 = 0
            holder.txtButton42.text = "W"
            holder.txtButton42.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton24.text = "L"
            holder.txtButton24.setBackgroundResource(R.drawable.buttonloser)
        }
        holder.txtButton43.setOnClickListener {
            userList[position].edited = true
            userList[position].p43 = 1
            userList[position].p34 = 0
            holder.txtButton43.text = "W"
            holder.txtButton43.setBackgroundResource(R.drawable.buttonwinner)
            holder.txtButton34.text = "L"
            holder.txtButton34.setBackgroundResource(R.drawable.buttonloser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.round_robin_row, parent, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName1Top = itemView.findViewById<TextView>(R.id.p1_top)
        val txtName2Top = itemView.findViewById<TextView>(R.id.p2_top)
        val txtName3Top = itemView.findViewById<TextView>(R.id.p3_top)
        val txtName4Top = itemView.findViewById<TextView>(R.id.p4_top)
        val txtName1 = itemView.findViewById<TextView>(R.id.p1)
        val txtName2 = itemView.findViewById<TextView>(R.id.p2)
        val txtName3 = itemView.findViewById<TextView>(R.id.p3)
        val txtName4 = itemView.findViewById<TextView>(R.id.p4)

        val txtButton12 = itemView.findViewById<Button>(R.id.p1_p2_button)
        val txtButton13 = itemView.findViewById<Button>(R.id.p1_p3_button)
        val txtButton14 = itemView.findViewById<Button>(R.id.p1_p4_button)
        val txtButton21 = itemView.findViewById<Button>(R.id.p2_p1_button)
        val txtButton23 = itemView.findViewById<Button>(R.id.p2_p3_button)
        val txtButton24 = itemView.findViewById<Button>(R.id.p2_p4_button)
        val txtButton31 = itemView.findViewById<Button>(R.id.p3_p1_button)
        val txtButton32 = itemView.findViewById<Button>(R.id.p3_p2_button)
        val txtButton34 = itemView.findViewById<Button>(R.id.p3_p4_button)
        val txtButton41 = itemView.findViewById<Button>(R.id.p4_p1_button)
        val txtButton42 = itemView.findViewById<Button>(R.id.p4_p2_button)
        val txtButton43 = itemView.findViewById<Button>(R.id.p4_p3_button)

        val number = itemView.findViewById<TextView>(R.id.round_number_rr)
    }

}