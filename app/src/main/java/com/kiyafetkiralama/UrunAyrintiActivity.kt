package com.kiyafetkiralama

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kiyafetkiralama.Fragment.UrunAyrintiFragment

class UrunAyrintiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urun_ayrinti)

        val urun = intent.getParcelableExtra<Urun>("urun")
        val key = intent.getStringExtra("key")

        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, UrunAyrintiFragment(urun,key)).commit()
    }
}