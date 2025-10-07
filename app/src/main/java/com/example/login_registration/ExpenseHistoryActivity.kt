package com.example.login_registration

import ExpenseAdapter
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpenseHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var DataDatabase: SQLiteDatabase
    private lateinit var fabAddExpense: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_history)

        recyclerView = findViewById(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fabAddExpense = findViewById(R.id.fabAddExpense)
        fabAddExpense.setOnClickListener {
        }

        DataDatabase = openOrCreateDatabase("ExpenseManage", MODE_PRIVATE, null)

        val expenses = mutableListOf<Expense>()

        val cursor: Cursor = DataDatabase.rawQuery("SELECT * FROM Expense", null)
        if (cursor.moveToFirst()) {
            do {
                val itemName = cursor.getString(cursor.getColumnIndexOrThrow("itemname"))
                val amount = cursor.getString(cursor.getColumnIndexOrThrow("amount"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))

                expenses.add(Expense(itemName, amount, date))
            } while (cursor.moveToNext())
        }
        cursor.close()

        recyclerView.adapter = ExpenseAdapter(expenses)
        fabAddExpense.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }

}
