package com.binararis.pilketos

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TerimaKasihActivity : AppCompatActivity() {
    lateinit var tv_Timer:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_terima_kasih)
        tv_Timer=findViewById(R.id.tvTimer)
        // Atur waktu hitung mundur (dalam milidetik)
        val waktuHitungMundur = 10000L // 10 detik

        // Membuat objek CountDownTimer
        object : CountDownTimer(waktuHitungMundur, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update tampilan waktu yang tersisa
                tv_Timer.text = "Waktu tersisa: ${millisUntilFinished / 1000} detik"
            }

            override fun onFinish() {
                // Mulai activity baru saat hitung mundur selesai
                val intent = Intent(this@TerimaKasihActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}