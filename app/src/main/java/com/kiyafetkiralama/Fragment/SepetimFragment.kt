package com.kiyafetkiralama.Fragment

import android.accounts.AbstractAccountAuthenticator
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kiyafetkiralama.Kullanici
import com.kiyafetkiralama.R
import com.kiyafetkiralama.Urun
import com.kiyafetkiralama.urunAdapter


class SepetimFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_sepetim, container, false)

        val firestoreDatabase = Firebase.firestore
        val database = Firebase.database.reference


        val currentUser = Firebase.auth.currentUser

        var user: Kullanici? = null;

        val urunler: ArrayList<Urun> =  arrayListOf();
        val dataSnapshotList = ArrayList<DataSnapshot>()

        val rcvView = view.findViewById<RecyclerView>(R.id.rv)
        rcvView.setHasFixedSize(true)
        rcvView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        firestoreDatabase.collection("users").whereEqualTo("uuid", currentUser!!.uid).limit(1).get().addOnSuccessListener { documents ->
            if (documents != null &&  documents.documents[0] != null) {
                val userDocument = documents.documents[0]
                user = userDocument!!.toObject<Kullanici>()

                user!!.Sepetim.forEach { id ->
                    Log.e("e",id)
                    var urunlerRef = database.child("urun").child(id)

                    urunlerRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val urun = dataSnapshot.getValue<Urun>()
                            urunler.add(urun!!)
                            dataSnapshotList.add(dataSnapshot)

                            var adapter = urunAdapter(requireContext(),urunler,dataSnapshotList)
                            rcvView.adapter = adapter
                            adapter.notifyDataSetChanged()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            //print error.message
                        }
                    })
                }


            } else {
                Log.d("HATA", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("HATA", "get failed with ", exception)
            }

        return view
    }
}