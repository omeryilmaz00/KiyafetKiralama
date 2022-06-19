package com.kiyafetkiralama.Fragment

import android.accounts.AbstractAccountAuthenticator
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.kiyafetkiralama.R
import com.kiyafetkiralama.Urun
import com.kiyafetkiralama.urunAdapter


class AnaSayfaFragment : Fragment() {
    private lateinit var database: DatabaseReference
    var recyclerView: RecyclerView? = null
    var rcv: RecyclerView? = null
    abstract class AccountAuthenticator(context: Context): AbstractAccountAuthenticator(context) {}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_ana_sayfa, container, false)

        val recyclerview = view.findViewById<RecyclerView>(R.id.fragmentrv)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        database = Firebase.database.reference

        var urunlerRef = database.child("urun")

        urunlerRef.addValueEventListener(object : ValueEventListener {
            val urunler = ArrayList<Urun>()
            val dataSnapshotList = ArrayList<DataSnapshot>()
            var intent: Intent = getIntent(context.toString())
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val urun = it.getValue<Urun>()
                    urunler.add(urun!!)
                    dataSnapshotList.add(it)
                }
                var adapter = urunAdapter(requireContext(),urunler, dataSnapshotList)
                recyclerview.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })

        return view
    }



}



