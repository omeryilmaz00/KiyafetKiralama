package com.kiyafetkiralama

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@IgnoreExtraProperties
@Parcelize
data class Urun(var urun_Baslik: String ="", var urun_Fiyat: String="", var urun_Aciklama: String="", var user_Uuid: String =  "", var resimler: ArrayList<String> = ArrayList<String>())  : Parcelable