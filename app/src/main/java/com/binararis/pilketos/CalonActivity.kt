package com.binararis.pilketos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class CalonActivity : AppCompatActivity() {
//    val database = FirebaseDatabase.getInstance("https://binarpilketos-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val database = FirebaseDatabase.getInstance("https://binarpilketos-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database.getReference("perhitungan")
    lateinit var cvcalon1:CardView
    lateinit var cvcalon2:CardView
    lateinit var cvcalon3:CardView
    lateinit var cvgolput:CardView
    lateinit var btPilih:Button
    var calondipilih:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calon)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            // Handle Firebase initialization errors
            Log.e("FirebaseInitialization", "Error initializing Firebase: ${e.message}")
            Toast.makeText(this, "Gagal menginisialisasi Firebase", Toast.LENGTH_SHORT).show()
            // You might want to handle the error gracefully, such as showing an error message or preventing further actions
        }

        cvcalon1=findViewById(R.id.cvCalon1)
        cvcalon2=findViewById(R.id.cvCalon2)
        cvcalon3=findViewById(R.id.cvCalon3)
        cvgolput=findViewById(R.id.cvGolput)
        btPilih=findViewById(R.id.btSimpan)
        btPilih.visibility = View.INVISIBLE
        warnaAwal()
        cvcalon1.setOnClickListener {
            calondipilih="1"
            warnaAwal()
            cvcalon1.setCardBackgroundColor(Color.parseColor("#2196F3"))
            btPilih.text="Pilih Calon 1"
            btPilih.visibility = View.VISIBLE
        }
        cvcalon2.setOnClickListener {
            calondipilih="2"
            warnaAwal()
            cvcalon2.setCardBackgroundColor(Color.parseColor("#2196F3"))
            btPilih.text="Pilih Calon 2"
            btPilih.visibility = View.VISIBLE
        }
        cvcalon3.setOnClickListener {
            calondipilih="3"
            warnaAwal()
            cvcalon3.setCardBackgroundColor(Color.parseColor("#2196F3"))
            btPilih.text="Pilih Calon 3"
            btPilih.visibility = View.VISIBLE
        }
        cvgolput.setOnClickListener {
            calondipilih="0"
            warnaAwal()
            cvgolput.setCardBackgroundColor(Color.parseColor("#2196F3"))
            btPilih.text="Pilih Golput"
            btPilih.visibility = View.VISIBLE
        }
        btPilih.setOnClickListener {
            simpanData(calondipilih)
            val goTerimaKasih =
                Intent(this@CalonActivity,
                    TerimaKasihActivity::class.java)
            startActivity(goTerimaKasih)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun warnaAwal(){
        cvcalon1.setCardBackgroundColor(Color.parseColor("#ebebeb"))
        cvcalon2.setCardBackgroundColor(Color.parseColor("#ebebeb"))
        cvcalon3.setCardBackgroundColor(Color.parseColor("#ebebeb"))
        cvgolput.setCardBackgroundColor(Color.parseColor("#ebebeb"))
    }

    fun simpanData(calon:String){
        val pilihanBaru = myRef.push()
        val postId = pilihanBaru.key.toString()
        val data = ModelPerhitungan(
            postId,calon)

        pilihanBaru.setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Log.e("SimpanData", "Error saving data: ${it.message}") // Add logging
                Toast.makeText(this, "Gagal Disimpan", Toast.LENGTH_SHORT).show()
            }
    }
}