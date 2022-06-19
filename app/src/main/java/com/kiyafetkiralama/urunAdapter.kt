package com.kiyafetkiralama

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot


class urunAdapter(private val mContext: Context, private val urunListesi:List<Urun>, private val snapshotList: List<DataSnapshot> ) : RecyclerView.Adapter<urunAdapter.CardTasarimNesneleriTutucu>(){

    inner class CardTasarimNesneleriTutucu(r:View):RecyclerView.ViewHolder(r){
         var imgUrun:ImageView
         var txtKullaniciIsim:TextView
         var txtFiyat:TextView
         init {
             txtKullaniciIsim = r.findViewById(R.id.txtUrunBaslik)
             imgUrun = r.findViewById(R.id.UrunResim)
             txtFiyat = r.findViewById(R.id.txtFiyat)
         }
    }

    override fun getItemCount(): Int {
        return  urunListesi.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimNesneleriTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.urunler,parent,false)
        return CardTasarimNesneleriTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimNesneleriTutucu, position: Int) {
        // TIKLANILAN URUN BU urun
        val urun = urunListesi[position]
        val snapshot = snapshotList[position]
        holder.txtKullaniciIsim.text = urun.urun_Baslik
        holder.txtFiyat.text = urun.urun_Fiyat
        if(urun.resimler.count()!=0)
            Glide.with(mContext).load(urun.resimler[0]).into(holder.imgUrun)


        holder.itemView.setOnClickListener {view ->
            val intent = Intent(view.context, UrunAyrintiActivity::class.java)
            intent.putExtra("urun", urun)
            intent.putExtra("key", snapshot.key)
            mContext.startActivity(intent)

        }
    }
}