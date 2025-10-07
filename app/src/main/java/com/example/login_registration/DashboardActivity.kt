package com.example.login_registration

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val name:EditText = findViewById(R.id.item_name)
        val amt:EditText = findViewById(R.id.amount)
        val date:EditText = findViewById(R.id.date)
        val addButton:Button = findViewById(R.id.addBtn)
        val viewHistory: TextView = findViewById(R.id.view_history)
        val DataDatabase: SQLiteDatabase = openOrCreateDatabase("ExpenseManage",MODE_PRIVATE,null)

        date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val dateString = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                date.setText(dateString)
            }, year, month, day)

            datePickerDialog.show()
        }
        addButton.setOnClickListener {
            val itemName = name.text.toString()
            val amount = amt.text.toString()
            val dateString = date.text.toString()

            if(itemName.isEmpty()){
                Toast.makeText(this, "Enter the name of item you want to enter.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(amount.isEmpty()){
                Toast.makeText(this, "Enter the amount.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(dateString.isEmpty()){
                Toast.makeText(this, "Select the date.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            DataDatabase.execSQL("CREATE TABLE IF NOT EXISTS Expense(itemname VARCHAR, amount VARCHAR, date VARCHAR)")

            DataDatabase.execSQL(
                "INSERT INTO Expense(itemname, amount, date) VALUES(?, ?, ?)",
                arrayOf(itemName, amount, dateString)
            )

            Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ExpenseHistoryActivity::class.java)
            startActivity(intent)
        }

        viewHistory.setOnClickListener{
            val intent = Intent(this,ExpenseHistoryActivity::class.java)
            startActivity(intent)
        }



    }
}