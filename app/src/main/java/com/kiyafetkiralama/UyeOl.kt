package com.kiyafetkiralama


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_uye_ol.*

class UyeOl : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_uye_ol)
        var KullaniciId = findViewById<TextView>(R.id.KullaniciId)
        var KullaniciMail = findViewById<TextView>(R.id.KullaniciMail)
        var KullaniciSifre = findViewById<TextView>(R.id.KullaniciSifre)
        var KullaniciSifreTekrar = findViewById<TextView>(R.id.KullaniciSifreTekrar)
        var kullaniniSozlesme = findViewById<RadioButton>(R.id.radıobuttonSozlesme)
        val firestoreDatabase = Firebase.firestore

        var button_uyeislem = findViewById<Button>(R.id.buttonUyeol)
        button_uyeislem.setOnClickListener {




               if(kullaniniSozlesme.isChecked == false)
               {
                   Toast.makeText(this, "lütfen kullanici sözleşmesini kabul ediniz", Toast.LENGTH_SHORT).show()
                   return@setOnClickListener
               }
            if (KullaniciId.text.toString() == "")
            {
                Toast.makeText(this, "lütfen kullanici adını boş bırakmayınız", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (KullaniciMail.text.toString() == "" || KullaniciSifre.text.toString() ==  "" )
            {
                Toast.makeText(this, "lütfen boş bırakmayınız ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            if (KullaniciSifre.text.toString() != KullaniciSifreTekrar.text.toString())
            {
                Toast.makeText(this, "lütfen şifrenizi kontrol ediniz ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            auth.createUserWithEmailAndPassword(KullaniciMail.text.toString(), KullaniciSifre.text.toString())
                .addOnCompleteListener { task ->
                    Log.e("omer", "GIRDI")
                    if (task.isSuccessful) {
                        var sepet : ArrayList<String> = arrayListOf()

                        val yeniKullanici = Kullanici(task.result.user!!.uid,KullaniciId.text.toString(),"","","",sepet)

                        val profileUpdates = userProfileChangeRequest {
                            displayName = KullaniciId.text.toString()
                        }

                        task.result.user!!.updateProfile(profileUpdates)



                        firestoreDatabase.collection("users").add(yeniKullanici)

                        Toast.makeText(this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, GirisYap::class.java))
                    } else {
                        Log.e("email", "lütfen farklı bir Email hesabı kullanın", task.exception)
                        Toast.makeText(this, "lütfen en azn 8 karakter girin", Toast.LENGTH_SHORT).show()
                    }
                }


        }


    }
}

