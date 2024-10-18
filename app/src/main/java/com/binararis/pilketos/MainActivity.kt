package com.binararis.pilketos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
            finish()
        }
        btLihatHasil.setOnClickListener {
            //awal custom dialog
            val builder = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.dialog_password, null)

            val etPassword = view.findViewById<EditText>(R.id.et_dialog_password)

            val btBatal = view.findViewById<Button>(R.id.bt_dialog_batal)
            val btOK = view.findViewById<Button>(R.id.bt_dialog_ok)
            builder.setView(view)
            val dialog = builder.create()
            dialog.setCancelable(false)
            btBatal.setOnClickListener {
                dialog.dismiss()
            }
            btOK.setOnClickListener {
                if (etPassword.text.toString().equals("binar")) {
                    dialog.dismiss()
                    val keHasil =
                        Intent(this@MainActivity,
                            HasilPerhitunganActivity::class.java)
                    startActivity(keHasil)
                } else {
                    etPassword.text.clear()
                    etPassword.requestFocus()
                    Toast.makeText(this@MainActivity, "Password Salah!!!", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show()
            //akhir custom dialog
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}