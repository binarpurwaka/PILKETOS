package com.binararis.pilketos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btnext:Button
    lateinit var btLihatHasil:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        btnext=findViewById(R.id.btNext)
        btLihatHasil=findViewById(R.id.btLihatHasil)
        btnext.setOnClickListener {
            val keCalon =
                Intent(this@MainActivity,
                    CalonActivity::class.java)
            startActivity(keCalon)
        }
        btLihatHasil.setOnClickListener {
            val keHasil =
                Intent(this@MainActivity,
                    HasilPerhitunganActivity::class.java)
            startActivity(keHasil)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}