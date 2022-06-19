package com.kiyafetkiralama


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.kiyafetkiralama.Fragment.MainActivity
import com.kiyafetkiralama.databinding.ActivityUyeOlBinding

class GirisYap : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giris_yap)

        auth = Firebase.auth
        var button_girisislem = findViewById<Button>(R.id.buttonGirisYapislem)

        button_girisislem.setOnClickListener {

            var email: String = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            var password: String = findViewById<EditText>(R.id.editTextTextPassword3).text.toString()

            if (email == "" || password == ""  )
            {

                Toast.makeText(this, "lütfen boş bırakmayınız ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "giriş başarılı", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else   {
                        Toast.makeText(this, "lütfen Email ve şifrenizi kontrol ediniz ", Toast.LENGTH_SHORT).show()

                    }

                }
        }
    }
}





