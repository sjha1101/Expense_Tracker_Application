package com.example.login_registration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn:Button = findViewById(R.id.login)
        val register: TextView = findViewById(R.id.register)


        loginBtn.setOnClickListener {
           val intent:Intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        register.setOnClickListener{
            val intent: Intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}