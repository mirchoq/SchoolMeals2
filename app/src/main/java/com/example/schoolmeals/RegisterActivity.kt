package com.example.schoolmeals

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.constraintlayout.widget.ConstraintLayout

import android.util.Log


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
        val editTextText3:View = findViewById(R.id.editTextText3)


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
                    editTextText4.isFocusable = false
                    editTextText4.isClickable = false
                    textView9.text = "Класс"
                } else {
                    // Скрываем лишние элементы
                    lastNameEditText.visibility = View.GONE
                    textView8.visibility = View.GONE
                    editTextText.visibility = View.VISIBLE
                    editTextText4.visibility = View.GONE
                    editTextText3.visibility = View.VISIBLE
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

    fun EndREG(view: View) {
        //Переменные элементов UI
        val view4A:View = findViewById(R.id.view4)
        val textView4S:TextView = findViewById(R.id.textView4)
        val roleSpinnerD:View = findViewById(R.id.roleSpinner)
        val battondef:Button = findViewById(R.id.button)
        val plaharegi:View = findViewById(R.id.endreges)
        val POMOGITEmnePLEASE:Spinner = findViewById(R.id.gradeSpinner3)
        val YaUstal:Spinner = findViewById(R.id.gradeSpinner)
        val view5D:View = findViewById(R.id.view5)
        val TextView9_1:TextView = findViewById(R.id.textView9)
        val view3_1:View = findViewById(R.id.view3)
        //
        val mains:ConstraintLayout = findViewById(R.id.main)
        val color = ContextCompat.getColor(this, R.color.fon)
        Log.d("MyApp", "Цвет: $color")
        mains.setBackgroundColor(color)
        //
        //Скрытие - Появление Элементов
        plaharegi.visibility = View.VISIBLE
        battondef.visibility = View.GONE
        roleSpinnerD.visibility = View.GONE
        textView4S.visibility = View.GONE
        view4A.visibility = View.GONE
        view5D.visibility = View.GONE
        TextView9_1.visibility = View.GONE
        POMOGITEmnePLEASE.visibility = View.GONE
        YaUstal.visibility = View.GONE
        view3_1.visibility = View.GONE


    }
}
