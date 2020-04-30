package com.example.scuploader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_first_year_subjects.*
import kotlinx.android.synthetic.main.activity_second_year_subjects.*

class SecondYearSubjects : AppCompatActivity() {
    var subjectCode="ds"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_year_subjects)

        val bundle: Bundle? = intent.extras
        val year = bundle!!.getString("yearcode")

        choose_subject_2.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            intent.putExtra("subcode", subjectCode)
            intent.putExtra("yearcode", year)

            startActivity(intent)
        }
    }

    fun onRadioButtonClicked(view: View){
        if(view is RadioButton){
            val checked = view.isChecked

            when(view.getId()){
                R.id.ds ->
                    if(checked){
                        subjectCode="ds"
                    }
                R.id.coa ->
                    if(checked){
                        subjectCode="coa"
                    }
                R.id.es ->
                    if(checked){
                        subjectCode="es"
                    }
                R.id.prp ->
                    if(checked){
                        subjectCode="prp"
                    }
                R.id.coaLab ->
                    if(checked){
                        subjectCode="coalab"
                    }
                R.id.ls ->
                    if(checked){
                        subjectCode="ls"
                    }
            }
        }
    }
}
