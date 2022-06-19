package com.kiyafetkiralama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kiyafetkiralama.Fragment.KullaniciAyrintiFragment

class KullaniciAyrintiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_ayrinti)

        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, KullaniciAyrintiFragment()).commit()
    }

}