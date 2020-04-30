package com.example.scuploader

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.File
import javax.security.auth.Subject

class UploadActivity : AppCompatActivity() {
    var FILE=1
    var year:String?=null
    var subject:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        val bundle: Bundle? = intent.extras
         subject = bundle!!.getString("subcode")
         year = bundle!!.getString("yearcode")

        Toast.makeText(this, "You are viewing $subject of $year", Toast.LENGTH_SHORT).show()

        choose_file.setOnClickListener {openFileChooser() }
    }

    fun openFileChooser(){
        var intent= Intent()
        intent.type="*/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if(requestCode==FILE){
            var fileUri=data?.data!!
            uploadFile(fileUri)
             var metaCursor = contentResolver.query(fileUri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME),null,null,null)!!
             metaCursor.moveToFirst()
             val  fileName = metaCursor.getString(0)
             metaCursor.close()
             chosen_file_name.text=fileName

        }

    }

    fun uploadFile(fileUri: Uri){

        //To get the original file name from the file url obtained from intent result
        var metaCursor = contentResolver.query(fileUri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME),null,null,null)!!
        metaCursor.moveToFirst()
        val  fileName = metaCursor.getString(0)
        metaCursor.close()
       
        val yearFolder=year
        val subjectFolder=subject
        Toast.makeText(this, "You are trying to store in $subjectFolder of $yearFolder", Toast.LENGTH_LONG).show()

        var storageRef = FirebaseStorage.getInstance().reference.child("file").child(fileName)
        //storage reference to be saved in storage
    if(yearFolder!=null) {
        if(subjectFolder!=null) {
             storageRef =
                FirebaseStorage.getInstance().reference.child("file/$yearFolder/$subjectFolder").child(fileName)
        }
    }


        storageRef.putFile(fileUri).addOnSuccessListener {                                                //uploading file in storage
           // Toast.makeText(this, "Upload file completed", Toast.LENGTH_SHORT).show()
            storageRef.downloadUrl.addOnSuccessListener {
                val urlToDatabase:String=it.toString()
               //Toast.makeText(this,urlToDatabase,Toast.LENGTH_LONG).show()
                if(fileName!=null)
                {
            storeFileDetailInDatabase("Pata nai",fileName)}
                else{
                    Toast.makeText(this,"Kuch th null h bhaiya",Toast.LENGTH_LONG).show()
                }
            }
        }
            .addOnFailureListener{
                Toast.makeText(this,"Beta firse jake tutorial dekho",Toast.LENGTH_LONG).show()
            }



    }

    fun storeFileDetailInDatabase(fileURL: String,fileName:String){

        val newFileName=fileName.removeSuffix(".pdf")
        val ref= FirebaseDatabase.getInstance().getReference("/files/"+newFileName)
        val file= com.example.scuploader.File(fileName,fileURL)

        ref.setValue(file).addOnFailureListener {
            Log.d("Bakaiti",it.toString())
            Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
        }
            .addOnSuccessListener {
                Log.d("Zinda",it.toString())
                Toast.makeText(this,"Kar diye kaam tamam",Toast.LENGTH_LONG).show()
            }

    }

}
class File(val fname:String,val fileURL:String)
