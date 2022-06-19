package com.kiyafetkiralama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kiyafetkiralama.Fragment.KullaniciAyrintiFragment
import com.kiyafetkiralama.Fragment.UrunlerimFragment

class UrunlerimActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_ayrinti)

        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, UrunlerimFragment()).commit()
    }

}