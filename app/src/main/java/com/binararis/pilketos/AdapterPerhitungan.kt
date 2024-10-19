package com.binararis.pilketos

import android.content.Context
import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdapterPerhitungan
    (private val listHasil: ArrayList<ModelCalon>) :
    RecyclerView.Adapter<AdapterPerhitungan.MyViewHolder>() {
    lateinit var appContext: Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNomorCalon: TextView = itemView.findViewById(R.id.tvCalon)
        val tvNamaCalon: TextView = itemView.findViewById(R.id.tvNamaCalon)
        val tvPerolehan: TextView = itemView.findViewById(R.id.tvJumlahPerolehan)
        val ivFotoCalon: ImageView = itemView.findViewById(R.id.ivFotoCalon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_calon, parent, false)
        appContext = parent.context
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = listHasil.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listHasil[position]
        holder.tvNomorCalon.text = "No Calon: " + item.idCalon
        holder.tvNamaCalon.text = item.nama
        if (item.idCalon.equals("1")) {
            holder.ivFotoCalon.setImageResource(R.drawable.baseline_person_24)
        }else if(item.idCalon.equals("2")){
            holder.ivFotoCalon.setImageResource(R.drawable.baseline_person_2_24)
        }else if(item.idCalon.equals("3")){
            holder.ivFotoCalon.setImageResource(R.drawable.baseline_person_3_24)
        }else{
            holder.ivFotoCalon.setImageResource(R.drawable.baseline_no_accounts_24)
        }
        holder.ivFotoCalon.setBackgroundResource(R.drawable.ic_launcher_background)
        //mencari jumlah data
        val database =
            FirebaseDatabase.getInstance("https://binarpilketos-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef = database.getReference("perhitungan")
        val query = myRef.orderByChild("idCalon").equalTo(item.idCalon)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val jml = snapshot.childrenCount
                    holder.tvPerolehan.text = "Jumlah Perolehan Suara : $jml"
                } else {
                    holder.tvPerolehan.text = "Jumlah Perolehan Suara : 0"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                holder.tvPerolehan.text = "0 Suara"
                Toast.makeText(appContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}