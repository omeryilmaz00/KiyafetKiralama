package com.kiyafetkiralama.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kiyafetkiralama.Kullanici
import com.kiyafetkiralama.R
import com.kiyafetkiralama.Urun
import kotlinx.android.synthetic.main.fragment_urun_ayrinti.*
import kotlinx.android.synthetic.main.urunler.*


class UrunAyrintiFragment(private val urun: Urun?, private val key:String?) : Fragment() {
    private var firebaseStore: FirebaseStorage? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_urun_ayrinti, container, false)
        val sepetButton = view.findViewById<TextView>(R.id.SepetButton)
        val urunBaslik = view.findViewById<TextView>(R.id.UrunBasligi)
        val currentUser = Firebase.auth.currentUser
        val firestoreDatabase = Firebase.firestore
        urunBaslik.setText(urun!!.urun_Baslik)

        val urunAciklama = view.findViewById<TextView>(R.id.UrunAciklama)
        urunAciklama.setText(urun!!.urun_Aciklama)

        val urunfiyat = view.findViewById<TextView>(R.id.UrunFiyat)
        urunfiyat.setText(urun!!.urun_Fiyat)

        val urunResim = view.findViewById<ImageView>(R.id.urunAyrıntıResim)
        if(urun!!.resimler.count()!=0)
            Glide.with(requireContext()).load(urun.resimler[0]).into(urunResim)
        firebaseStore = FirebaseStorage.getInstance()


        var userDocument: DocumentSnapshot? = null;
        var user: Kullanici? = null;
        firestoreDatabase.collection("users").whereEqualTo("uuid", currentUser!!.uid).limit(1).get().addOnSuccessListener { documents ->
            if (documents != null &&  documents.documents[0] != null) {
                userDocument = documents.documents[0]
                user = userDocument!!.toObject<Kullanici>()

            } else {
                Log.d("HATA", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("HATA", "get failed with ", exception)
            }



        sepetButton.setOnClickListener {
            user!!.Sepetim!!.add(key!!)
            userDocument!!.reference.update("sepetim", user!!.Sepetim)
          }
        return view;
    }

}