package com.binararis.pilketos

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HasilPerhitunganActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance("https://binarpilketos-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database.getReference("calon")
    lateinit var calonList: ArrayList<ModelCalon>

    lateinit var recyclerView: RecyclerView
    lateinit var tvJumlah: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hasil_perhitungan)
        FirebaseApp.initializeApp(this)
        recyclerView = findViewById(R.id.rvHasilPerhitungan)
        tvJumlah = findViewById(R.id.tvTotalPerolehan)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        calonList = arrayListOf<ModelCalon>()
        getData()
        getTotal()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getData() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    calonList.clear()
                    for (dataSnapshot in snapshot.children){
                        val calon = dataSnapshot.getValue(ModelCalon::class.java)
                        calonList.add(calon!!)
                    }
                    val adapter = AdapterPerhitungan(calonList)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@HasilPerhitunganActivity,"Data Kosong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HasilPerhitunganActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getTotal(){
        //mencari jumlah data
        val database = FirebaseDatabase.getInstance("https://binarpilketos-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef = database.getReference("perhitungan")
//        val query = myRef.orderByChild("idCalon").equalTo(item.idCalon)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val jml = snapshot.childrenCount
                    tvJumlah.text = "$jml Suara"
                } else {
                    tvJumlah.text = "0 Suara"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                tvJumlah.text = "Belum Ada Suara Masuk"
                Toast.makeText(this@HasilPerhitunganActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}