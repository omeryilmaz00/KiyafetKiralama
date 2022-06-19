package com.kiyafetkiralama.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kiyafetkiralama.*
import kotlinx.android.synthetic.main.fragment_ayarlar.*
import kotlinx.android.synthetic.main.fragment_urun_ayrinti.*
import java.util.*

class AyarlarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    val    view = inflater.inflate(R.layout.fragment_ayarlar, container, false)

        var KullaniciAyarButton = view?.findViewById<TextView>(R.id.KullaniciAyarlar)
        var ProfilFoto = view.findViewById<ImageView>(R.id.ProfilResmim)
        var displayName = view?.findViewById<TextView>(R.id.displayName)
        var UrunlerimButton = view?.findViewById<TextView>(R.id.Urunlerim)
        val currentUser = Firebase.auth.currentUser
        val firestoreDatabase = Firebase.firestore
        displayName!!.setText(currentUser!!.displayName)
        var userDocument: DocumentSnapshot? = null;

        firestoreDatabase.collection("users").whereEqualTo("uuid", currentUser!!.uid).limit(1).get().addOnSuccessListener { documents ->
            if (documents != null &&  documents.documents[0] != null) {
                userDocument = documents.documents[0]
                val user = userDocument!!.toObject<Kullanici>()


                Glide.with(requireContext()).load(user!!.Profil_Resim).into(ProfilFoto)

            } else {
                Log.d("HATA", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("HATA", "get failed with ", exception)
            }


         var sepetimButton = view.findViewById<TextView>(R.id.Sepetim)
        sepetimButton?.setOnClickListener {
            val yeniIntent = Intent(view.context, SepetimActivity::class.java)
            startActivity(yeniIntent)
        }
        KullaniciAyarButton?.setOnClickListener {
            val yeniIntent = Intent(view.context, KullaniciAyrintiActivity::class.java)
            startActivity(yeniIntent)
        }
        UrunlerimButton?.setOnClickListener {
            val yeniIntent = Intent(view.context, UrunlerimActivity::class.java)
            startActivity(yeniIntent)
        }
        return view;

    }





}