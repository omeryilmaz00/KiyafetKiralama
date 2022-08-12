package com.kiyafetkiralama.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kiyafetkiralama.Kullanici
import com.kiyafetkiralama.R
import kotlinx.android.synthetic.main.fragment_kullanici_ayrinti.*
import java.util.*
import kotlin.collections.ArrayList


class KullaniciAyrintiFragment() : Fragment() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imageView: ImageView
    lateinit var btn_choose_image: TextView
    lateinit var btn_upload_image: TextView
    lateinit var Profilresim: String
    lateinit var sepet : ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view= inflater.inflate(R.layout.fragment_kullanici_ayrinti, container, false)


        val currentUser = Firebase.auth.currentUser
        val firestoreDatabase = Firebase.firestore

        var kullaniciAd: EditText? = view.findViewById<EditText>(R.id.KullaniciAd)
        var KullaniciCinsiyet: EditText? = view.findViewById<EditText>(R.id.KullaniciCinsiyet)
        var KullaniciTelefon: EditText = view.findViewById<EditText>(R.id.KullaniciTelefon)

        imageView = view.findViewById(R.id.imageProfil)

        var userDocument: DocumentSnapshot? = null;

        firestoreDatabase.collection("users").whereEqualTo("uuid", currentUser!!.uid).limit(1).get().addOnSuccessListener { documents ->
            if (documents != null &&  documents.documents[0] != null) {
                userDocument = documents.documents[0]
                val user = userDocument!!.toObject<Kullanici>()
                KullaniciTelefon.setText(user!!.kullanici_Telefon)
                KullaniciCinsiyet!!.setText(user!!.Kullanici_Cinsiyet)
                kullaniciAd!!.setText(user!!.kullanici_Ad)
                sepet = user.Sepetim!!
                Profilresim=user.Profil_Resim
                Glide.with(requireContext()).load(user!!.Profil_Resim).into(imageView)

            } else {
                Log.d("HATA", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("HATA", "get failed with ", exception)
            }

        btn_choose_image = view.findViewById(R.id.fotografEkle)
        btn_upload_image = view.findViewById(R.id.fotografYukle)

        var KaydetButton = view.findViewById<Button>(R.id.KaydetButton)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://kiyafetkiralama.appspot.com")

        btn_choose_image.setOnClickListener { launchGallery() }


        btn_upload_image.setOnClickListener { uploadImage() }

        KaydetButton.setOnClickListener {


            if(kullaniciAd!!.text.toString() == "") {
                Toast.makeText(this.context, "Lütfen kullanıcı adını boş bırakmayınız", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if(KullaniciTelefon!!.text.toString() == "") {
                Toast.makeText(this.context, "Lütfen numaranızı yazınız", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if(Profilresim.count() == 0) {
                Toast.makeText(this.context, "lütfen profil fotoğrafı ekleyiniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            val kullanici = Kullanici(currentUser!!.uid,kullaniciAd!!.text.toString(),KullaniciTelefon!!.text.toString(),KullaniciCinsiyet!!.text.toString(),
                Profilresim,sepet
            );

            val userRef = firestoreDatabase.collection("users").document(userDocument!!.id)

            userRef.set(kullanici)


            val profileUpdates = userProfileChangeRequest {

                displayName = KullaniciAd!!.text.toString()



            }

            currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context,"Basarıyla kaydedildi",Toast.LENGTH_LONG).show();
                    }
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
            imageView.setImageURI(filePath)
        }
    }
    private fun uploadImage() {

        if(filePath != null){
            val ref = storageReference?.child("ProfilImages/" + UUID.randomUUID().toString())
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
                    Profilresim  = task.result.toString()
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

