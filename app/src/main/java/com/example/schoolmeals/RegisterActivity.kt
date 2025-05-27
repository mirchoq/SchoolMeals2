package com.example.schoolmeals

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import java.io.File
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // === Элементы UI ===
        val roleSpinner: Spinner = findViewById(R.id.roleSpinner)
        val view5:View = findViewById(R.id.view5)
        val lastNameEditText: EditText = findViewById(R.id.editTextText)     // Фамилия
        val firstNameEditText: EditText = findViewById(R.id.editTextText2)   // Имя
        val middleNameEditText: EditText = findViewById(R.id.editTextText3)  // Отчество
        val curatorEditText: EditText = findViewById(R.id.editTextText4)      // Руководитель

        val textView9: TextView = findViewById(R.id.textView9)                // Заголовок "Класс"
        val textView8: View = findViewById(R.id.textView8)                    // Подпись "Руководитель"
        val textView5: View = findViewById(R.id.textView5)                    // Подпись "Фамилия" — всегда видима

        val roles = listOf("Ученик", "Учитель")
        val roleAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = roleAdapter
        roleSpinner.setSelection(0)

        // === Обработчик выбора роли ===
        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRole = roles[position]

                // editTextText3 (Отчество) всегда отображается
                findViewById<EditText>(R.id.editTextText3).visibility = View.VISIBLE

                if (selectedRole == "Ученик") {
                    // Показываем все поля
                    lastNameEditText.visibility = View.VISIBLE
                    firstNameEditText.visibility = View.VISIBLE
                    middleNameEditText.visibility = View.VISIBLE
                    curatorEditText.visibility = View.VISIBLE
                    textView8.visibility = View.VISIBLE
                    textView5.visibility = View.VISIBLE
                    textView9.text = "Класс"

                    findViewById<Spinner>(R.id.gradeSpinner3).visibility = View.VISIBLE
                    findViewById<Spinner>(R.id.gradeSpinner).visibility = View.VISIBLE

                } else {
                    // Для учителя остаются видимыми все поля, кроме "Руководитель" и textView8
                    lastNameEditText.visibility = View.VISIBLE
                    firstNameEditText.visibility = View.VISIBLE
                    middleNameEditText.visibility = View.VISIBLE
                    curatorEditText.visibility = View.GONE
                    textView8.visibility = View.GONE
                    textView9.text = "Руковожу классом"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // === Инициализация спиннера цифры класса (1 - 11) + "-" как дефолтное значение ===
        val gradeSpinner3: Spinner = findViewById(R.id.gradeSpinner3)
        val grades = listOf("-") + (1..11).map { it.toString() }
        val gradeAdapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, grades)
        gradeAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradeSpinner3.adapter = gradeAdapter3
        gradeSpinner3.setSelection(0)

        // === Инициализация спиннера буквы класса (А - Д) + "-" как дефолтное значение ===
        val gradeSpinner: Spinner = findViewById(R.id.gradeSpinner)
        val letters = listOf("-") + listOf("А", "Б", "В", "Г", "Д")
        val letterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, letters)
        letterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradeSpinner.adapter = letterAdapter
        gradeSpinner.setSelection(0)
    }

    fun EndREG(view: View) {
        // === Получаем элементы UI ===
        val roleSpinnerD: Spinner = findViewById(R.id.roleSpinner)
        val lastNameEditText: EditText = findViewById(R.id.editTextText)       // Фамилия
        val firstNameEditText: EditText = findViewById(R.id.editTextText2)     // Имя
        val middleNameEditText: EditText = findViewById(R.id.editTextText3)    // Отчество
        val teacherField: EditText = findViewById(R.id.editTextText4)         // Руководитель (ученик)

        val mains = findViewById<View>(R.id.main) as android.view.ViewGroup

        // === Проверка на заполненность ===
        val selectedRole = roleSpinnerD.selectedItem.toString()
        val isLastNameFilled = lastNameEditText.text.toString().trim().isNotEmpty()
        val isFirstNameFilled = firstNameEditText.text.toString().trim().isNotEmpty()
        val isMiddleNameFilled = middleNameEditText.text.toString().trim().isNotEmpty()

        var allFieldsFilled = isLastNameFilled && isFirstNameFilled && isMiddleNameFilled

        if (selectedRole == "Ученик") {
            val isTeacherFieldFilled = teacherField.text.toString().trim().isNotEmpty()
            val gradeNumber = findViewById<Spinner>(R.id.gradeSpinner3).selectedItem.toString() != "-"
            val gradeLetter = findViewById<Spinner>(R.id.gradeSpinner).selectedItem.toString() != "-"

            allFieldsFilled = allFieldsFilled && isTeacherFieldFilled && gradeNumber && gradeLetter
        } else if (selectedRole == "Учитель") {
            val gradeNumber = findViewById<Spinner>(R.id.gradeSpinner3).selectedItem.toString() != "-"
            val gradeLetter = findViewById<Spinner>(R.id.gradeSpinner).selectedItem.toString() != "-"

            allFieldsFilled = allFieldsFilled && gradeNumber && gradeLetter
        }

        if (!allFieldsFilled) {
            Toast.makeText(this, "Заполните ВСЕ поля", Toast.LENGTH_SHORT).show()
            return
        }

        // === Собираем данные из полей ===
        val surname = lastNameEditText.text.toString().trim()
        val name = firstNameEditText.text.toString().trim()
        val patronymic = middleNameEditText.text.toString().trim()
        val className = findViewById<Spinner>(R.id.gradeSpinner3).selectedItem.toString()
        val classLetter = findViewById<Spinner>(R.id.gradeSpinner).selectedItem.toString()

        val filledText = if (selectedRole == "Ученик") {
            val curator = teacherField.text.toString().trim()
            """
                Строка 1: Фамилия - $surname
                Строка 2: Имя - $name
                Строка 3: Отчество - $patronymic
                Строка 4: Руководитель - $curator
                Строка 5: Класс - $className$classLetter
            """.trimIndent()
        } else {
            """
                Строка 1: Фамилия - $surname
                Строка 2: Имя - $name
                Строка 3: Отчество - $patronymic
                Строка 4: Руководитель - 
                Строка 5: Класс - $className$classLetter
            """.trimIndent()
        }

        // === Сохраняем файл во внутреннем хранилище ===
        try {
            val filename = "student_info.txt"
            val file = File(filesDir, filename)
            file.writeText(filledText)

            // === Показываем путь к файлу ===
            val filePath = file.absolutePath
            Toast.makeText(this, "Файл сохранён:\n$filePath", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show()
        }

        // === Меняем цвет фона ===
        mains.setBackgroundColor(ContextCompat.getColor(this, R.color.fon))

        // === Скрываем/показываем элементы после регистрации ===
        findViewById<View>(R.id.endreges).visibility = View.VISIBLE
        findViewById<Button>(R.id.button).visibility = View.GONE
        findViewById<Spinner>(R.id.roleSpinner).visibility = View.GONE
        findViewById<TextView>(R.id.textView4).visibility = View.GONE
        findViewById<View>(R.id.view5).visibility =View.GONE
        findViewById<View>(R.id.view4).visibility = View.GONE
        // textView5 НЕ скрываем, потому что это подпись "Фамилия"
        findViewById<TextView>(R.id.textView9).visibility = View.GONE
        findViewById<Spinner>(R.id.gradeSpinner3).visibility = View.GONE
        findViewById<Spinner>(R.id.gradeSpinner).visibility = View.GONE
        findViewById<View>(R.id.view3).visibility = View.GONE

        // Скрытие textView8, если это не ученик
        if (roleSpinnerD.selectedItem.toString() != "Ученик") {
            findViewById<View>(R.id.textView8).visibility = View.GONE
        }
    }
}