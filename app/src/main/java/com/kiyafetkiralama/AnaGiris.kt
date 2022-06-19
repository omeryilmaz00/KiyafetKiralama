package com.kiyafetkiralama

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kiyafetkiralama.Fragment.MainActivity

class AnaGiris : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ana_giris)
        


        
              
        var ArkaPlan = findViewById<View>(R.id.arkaplan) as ImageView
        ArkaPlan.setImageResource(R.drawable.anagiris)
        var Button_giris = findViewById<Button>(R.id.buttonGirisYap)
        var Button_UyeOl = findViewById<Button>(R.id.buttonUyeol)
        var TextButton = findViewById<TextView>(R.id.UyeOlmadanDevamEt)
        var Urunbaslik = findViewById<TextView>(R.id.UrunBasligi)
        var Urunaciklama = findViewById<TextView>(R.id.UrunAciklama)



        TextButton.setOnClickListener {

            val yeniIntent = Intent(this@AnaGiris, MainActivity::class.java)
            startActivity(yeniIntent)
        }
        Button_UyeOl.setOnClickListener {

            val yeniIntent = Intent(this@AnaGiris,UyeOl::class.java)

            startActivity(yeniIntent)
        }
        Button_giris.setOnClickListener {

            val yeniIntent = Intent(this@AnaGiris,GirisYap::class.java)

            startActivity(yeniIntent)
        }


    }
}