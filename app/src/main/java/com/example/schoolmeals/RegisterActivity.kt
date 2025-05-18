package com.example.schoolmeals

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // === Элементы UI ===
        val roleSpinner: Spinner = findViewById(R.id.roleSpinner)
        val lastNameEditText: View = findViewById(R.id.editTextText) // Фамилия
        val gradeSpinnerFREE: Spinner = findViewById(R.id.gradeSpinner3)   // Цифра класса
        val gradeSpinners: Spinner = findViewById(R.id.gradeSpinner)     // Буква класса
        val textView9: TextView = findViewById(R.id.textView9)       // Заголовок "Класс"
        val textView8: View = findViewById(R.id.textView8)           // Подпись "Руководитель"
        val textView5: View = findViewById(R.id.textView5)          // Подпись "Фамилии"
        val editTextText: View = findViewById(R.id.editTextText)
        val editTextText4:View = findViewById(R.id.editTextText4) //Плаха "фамилии"

        // === Наполнение спиннера Роли (Ученик / Учитель) ===
        val roles = listOf("Ученик", "Учитель")
        val roleAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = roleAdapter
        roleSpinner.setSelection(0)

        // === Обработчик выбора роли ===
        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRole = roles[position]

                if (selectedRole == "Ученик") {
                    // Показываем все элементы класса и фамилии
                    lastNameEditText.visibility = View.VISIBLE
                    textView5.visibility = View.VISIBLE
                    gradeSpinnerFREE.visibility = View.VISIBLE
                    gradeSpinners.visibility = View.VISIBLE
                    textView8.visibility = View.VISIBLE
                    editTextText4.visibility = View.VISIBLE
                    textView9.text = "Класс"
                } else {
                    // Скрываем лишние элементы
                    lastNameEditText.visibility = View.GONE
                    textView5.visibility = View.GONE
                    textView8.visibility = View.GONE
                    editTextText.visibility = View.GONE
                    editTextText4.visibility = View.GONE
                    textView9.text = "Руковожу классом"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // === Инициализация спиннера цифры класса (1 - 11) ===
        val gradeSpinner3: Spinner = findViewById(R.id.gradeSpinner3)
        val grades = (1..11).map { it.toString() }
        val gradeAdapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, grades)
        gradeAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradeSpinner3.adapter = gradeAdapter3
        gradeSpinner3.setSelection(0)

        // === Инициализация спиннера буквы класса (А - Д) ===
        val gradeSpinner: Spinner = findViewById(R.id.gradeSpinner)
        val letters = listOf("А", "Б", "В", "Г", "Д")
        val letterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, letters)
        letterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradeSpinner.adapter = letterAdapter
        gradeSpinner.setSelection(0)
    }
}