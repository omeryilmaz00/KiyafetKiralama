package com.kiyafetkiralama

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@IgnoreExtraProperties
@Parcelize
data class Kullanici(var uuid: String ="" ,var kullanici_Ad: String="",var kullanici_Telefon: String="", var Kullanici_Cinsiyet: String ="",var Profil_Resim: String = "" ,var Sepetim : ArrayList<String> = arrayListOf()): Parcelable
