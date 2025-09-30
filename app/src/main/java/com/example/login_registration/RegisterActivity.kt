package com.example.login_registration

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val confirmPassword: EditText = findViewById(R.id.confirm_password)
        val male: RadioButton = findViewById(R.id.male)
        val female: RadioButton = findViewById(R.id.female)
        val submitBtn: Button = findViewById(R.id.submit)
        val login:TextView = findViewById(R.id.login)
        val DataDatabase:SQLiteDatabase = openOrCreateDatabase("Data",MODE_PRIVATE,null)
        val sportsCheckbox: CheckBox = findViewById(R.id.sports)
        val dancingCheckbox: CheckBox = findViewById(R.id.dancing)
        val travelingCheckbox: CheckBox = findViewById(R.id.traveling)

         submitBtn.setOnClickListener {
            val emailInput = email.text.toString().trim()
            val passwordInput = password.text.toString().trim()
            val confirmPasswordInput = confirmPassword.text.toString().trim()
            val gender = when {
                male.isChecked -> "Male"
                female.isChecked -> "Female"
                else -> ""
            }
             val selectedHobbies = mutableListOf<String>()
             if (sportsCheckbox.isChecked) selectedHobbies.add("Sports")
             if (dancingCheckbox.isChecked) selectedHobbies.add("Dancing")
             if (travelingCheckbox.isChecked) selectedHobbies.add("Traveling")

            if(emailInput.isEmpty()){
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(passwordInput.isEmpty()){
                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(confirmPasswordInput.isEmpty()){
                Toast.makeText(this, "Enter confirm password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(passwordInput != confirmPasswordInput){
                Toast.makeText(this, "Password doesn't match.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (gender.isEmpty()) {
                Toast.makeText(this, "Please select a gender.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
             val hobbies = selectedHobbies.joinToString(",")

             // Create table if not exists with an added hobbies column
             DataDatabase.execSQL("CREATE TABLE IF NOT EXISTS USER(email VARCHAR, password VARCHAR, gender VARCHAR, hobbies VARCHAR)")

             // Insert user data into the table including hobbies
             DataDatabase.execSQL(
                 "INSERT INTO USER(email, password, gender, hobbies) VALUES(?, ?, ?, ?)",
                 arrayOf(emailInput, passwordInput, gender, hobbies)
             )

             Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
             val intent = Intent(this, DashboardActivity::class.java)
             startActivity(intent)
         }
        login.setOnClickListener{
            val intent: Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

}