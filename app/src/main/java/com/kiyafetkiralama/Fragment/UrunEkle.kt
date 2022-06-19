package com.kiyafetkiralama.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kiyafetkiralama.R
import com.kiyafetkiralama.Urun
import java.util.*


class UrunEkle : Fragment() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_choose_image: Button
    lateinit var btn_upload_image: Button
    private var urunResimler:ArrayList<String> = ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= inflater.inflate(R.layout.fragment_urun_ekle, container, false)

        btn_choose_image = view.findViewById(R.id.FotografEkle)
        btn_upload_image = view.findViewById(R.id.FotografYukle)
        imagePreview = view.findViewById(R.id.image_preview)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://kiyafetkiralama.appspot.com")


        btn_choose_image.setOnClickListener { launchGallery() }


        btn_upload_image.setOnClickListener { uploadImage() }






        var kiralabutton = view.findViewById<Button>(R.id.KiralaButton)
        kiralabutton.setOnClickListener {
            var urunbaslik: String = view.findViewById<EditText>(R.id.UrunBasligi).text.toString()
            var urunaciklama: String = view.findViewById<EditText>(R.id.UrunAciklama).text.toString()
            var urunFiyat: String = view.findViewById<EditText>(R.id.urunFiyat).text.toString()
            val database = FirebaseDatabase.getInstance()
            val refUrun = database.getReference("urun")
            val userUid: String = Firebase.auth.currentUser!!.uid
             if (urunResimler.count()<=5)
        {
            Toast.makeText(this.context, "En az 5 fotoğraf eklemelisiniz....", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }
            if(urunResimler.count()>=15) {
                Toast.makeText(this.context, "Cok fazla resim yuklendi. En fazla 15 resim yükleyebilirsiniz..", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            val urun = Urun(urunbaslik!!.toString(),urunFiyat.toString(), urunaciklama.toString(),userUid, urunResimler)
            try {
                refUrun.push().setValue(urun)
                Toast.makeText(this.context, "ürün başarıyla eklendi ", Toast.LENGTH_SHORT).show()
            } catch(err:Exception) {
            Log.w("TAG",err.toString())
            }
        }

        return view;
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            imagePreview.setImageURI(filePath)
        }
    }
    private fun uploadImage() {

        if(filePath != null){
            val ref = storageReference?.child("myImages/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            uploadTask!!.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    urunResimler.add(task.result.toString())
                    Toast.makeText(this.context, "Resim başarıyla yüklendi!", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle failures
                    // ...
                }
            }

        }else{
            Toast.makeText(this.context, "resim yüklenemedi", Toast.LENGTH_SHORT).show()
        }
    }


}
