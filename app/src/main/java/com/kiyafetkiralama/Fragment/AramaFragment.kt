package com.kiyafetkiralama.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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


class AramaFragment : Fragment() {
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_arama, container, false)

        val urunlerimrv = view.findViewById<RecyclerView>(R.id.rcvSearch)
        urunlerimrv.setHasFixedSize(true)
        urunlerimrv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        database = Firebase.database.reference

        var urunlerRef = database.child("urun")

        val urunler = ArrayList<Urun>()
        val dataSnapshotList = ArrayList<DataSnapshot>()




        val searchV = view.findViewById<SearchView>(R.id.srcView)
        searchV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                urunlerRef.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        urunler.clear()

                        dataSnapshot.children.forEach {
                            val urun = it.getValue<Urun>()
                            if(urun!!.urun_Baslik.contains(newText))
                                urunler.add(urun)
                                dataSnapshotList.add(it)
                        }

                        var adapter = urunAdapter(requireContext(),urunler,dataSnapshotList)
                        urunlerimrv.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        //print error.message
                    }
                })
                return true;
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })

        return view
    }


}