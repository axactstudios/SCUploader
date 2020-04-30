package com.example.scuploader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_year_selector.*

class YearSelector : AppCompatActivity() {

    var yearCode="1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_year_selector)
        choose_subject.setOnClickListener {
            if(yearCode=="1") {
                val intent = Intent(this, FirstYearSubjects::class.java)
                intent.putExtra("yearcode", "FirstYear")
                Toast.makeText(this, "You have selected $yearCode year", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            else{
                val intent = Intent(this, SecondYearSubjects::class.java)
                intent.putExtra("yearcode", "SecondYear")
                Toast.makeText(this, "You have selected $yearCode year", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }

    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.firstYear ->
                    if (checked) {
                       yearCode="1"
                    }
                R.id.secondYear ->
                    if (checked) {
                      yearCode="2"
                    }
            }
        }

    }
}
