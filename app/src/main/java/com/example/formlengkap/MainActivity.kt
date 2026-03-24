package com.example.formlengkap

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind views
        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val spinner = findViewById<Spinner>(R.id.spinnerCountry)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val checkBoxes = listOf<CheckBox>(
            findViewById(R.id.cbCoding), findViewById(R.id.cbDesign),
            findViewById(R.id.cbMusic), findViewById(R.id.cbTravel)
        )

        // 04: Custom Spinner Data
        val countries = arrayOf("Indonesia", "Singapore", "Japan", "Switzerland")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        spinner.adapter = adapter

        // 02: Real-time Validation
        etEmail.doOnTextChanged { text, _, _, _ ->
            if (!Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()) {
                tilEmail.error = "Invalid email format"
            } else {
                tilEmail.error = null
            }
        }

        // 05: Gesture Interaction (Long Press)
        btnSubmit.setOnLongClickListener {
            Toast.makeText(this, "Special Action: Clearing Form...", Toast.LENGTH_SHORT).show()
            // Reset logic here if needed
            true
        }

        // 04: AlertDialog on Submit
        btnSubmit.setOnClickListener {
            if (validateForm(tilName, tilEmail, checkBoxes)) {
                showConfirmationDialog()
            }
        }
    }

    private fun validateForm(tilName: TextInputLayout, tilEmail: TextInputLayout, cbList: List<CheckBox>): Boolean {
        var isValid = true

        // Check Name
        if (tilName.editText?.text.isNullOrEmpty()) {
            tilName.error = "Name cannot be empty"
            isValid = false
        }

        // Check Hobbies (Min 3)
        val selectedHobbies = cbList.filter { it.isChecked }.size
        if (selectedHobbies < 3) {
            Toast.makeText(this, "Select at least 3 hobbies", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Registration")
            .setMessage("Are you sure all data is correct?")
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No", null)
            .show()
    }
}