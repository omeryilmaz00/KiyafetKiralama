package com.kiyafetkiralama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kiyafetkiralama.Fragment.KullaniciAyrintiFragment
import com.kiyafetkiralama.Fragment.SepetimFragment

class SepetimActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sepetim)
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, SepetimFragment()).commit()
    }
}