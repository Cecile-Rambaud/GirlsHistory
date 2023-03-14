package fr.cecilerambaud.ananke.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.cecilerambaud.ananke.MainActivity
import fr.cecilerambaud.ananke.R
import fr.cecilerambaud.ananke.WomanModel
import fr.cecilerambaud.ananke.WomanRepository
import fr.cecilerambaud.ananke.WomanRepository.Singleton.downloadUri
import java.util.*

class AddWomanFragment (
    private val context: MainActivity
) : Fragment() {

    private var file: Uri? = null

    private var uploadedImage: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_woman, container, false)

       // nationalitySpinner = view.findViewById(R.id.spinner_nationality_woman)

        // RECUPERATION uploadedImage POUR ASSOCIER SON COMPOSANT
        uploadedImage = view.findViewById(R.id.preview_image)

        // RECUPERATION DU BOUTON IMAGE DE PROFIL
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        // CHERCHER DES IMAGES SUR SON TELEPHONE
        pickupImageButton.setOnClickListener {  pickupImage()  }

        // RECUPERATION DU BOUTON "Mettre à jour"
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener { sendForm(view) }

        return view
    }

    private fun sendForm(view: View) {
        val repo = WomanRepository()
        repo.uploadImage(file!!) {
            val womanFirstName = view.findViewById<EditText>(R.id.hint_first_name_woman).text.toString()
            val womanLastName = view.findViewById<EditText>(R.id.hint_last_name_woman).text.toString()
            val womanNationality = view.findViewById<EditText>(R.id.hint_nationality_woman).text.toString()
            val womanBirth = view.findViewById<EditText>(R.id.hin_birth_woman).text.toString()
            val womanActivity = view.findViewById<Spinner>(R.id.spinner_activity_woman).selectedItem.toString()
            val womanDescription = view.findViewById<EditText>(R.id.hint_description_woman).text.toString()
            val downloadImageUrl = downloadUri

            // CREATION NOUVEL OBJET USER
            val woman = WomanModel(
                UUID.randomUUID().toString(),
                womanActivity,
                womanFirstName,
                womanLastName,
                womanNationality,
                womanBirth,
                womanDescription,
                downloadImageUrl.toString()
            )
            repo.insertWoman(woman)
        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Sélectionnez une photo"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK) {
            // VERIFICATION DES DONNES
            if(data == null || data.data == null) return

            // RECUPERATION DE L'IMAGE SELECTIONNEE
            file = data?.data

            // METTRE A JOUR L'IMAGE
            uploadedImage?.setImageURI(file)
        }
    }
}