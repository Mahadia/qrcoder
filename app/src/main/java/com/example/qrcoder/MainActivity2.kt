package com.example.qrcoder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val intent = intent
        var str = intent.getStringExtra("Information").toString()
        var text = findViewById<TextView>(R.id.tv_textView1)

        loadData()
            // creation de notre intent
            val monIntentRetour: Intent = Intent(this, MainActivity::class.java)

            //clic sur le bouton
            buttonPanel2.setOnClickListener {

                startActivity(monIntentRetour)
            }
        }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY",null)
        tv_textView1.text = savedString
    }
    }
