package fr.cecilerambaud.ananke

import android.app.Dialog
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.cecilerambaud.ananke.adapter.WomanAdapter

class WomanPopup(
    private val adapter : WomanAdapter,
    private val currentWoman: WomanModel
    ) : Dialog(adapter.context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_women_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupHeartButton()
    }


    private fun updateHeart(button: ImageView) {
        if(currentWoman.liked) {
            button.setImageResource(R.drawable.ic_favorite)
        }
        else {
            button.setImageResource(R.drawable.ic_unfavorite)
        }
    }

    private fun setupHeartButton() {
        // AJOUTER UN SPECIMEN DANS LES FAVORIS
        val heartButton = findViewById<ImageView>(R.id.ic_unfavorite)
        updateHeart(heartButton)

        // INTERACTION
        heartButton.setOnClickListener {
            currentWoman.liked = !currentWoman.liked
            val repo = WomanRepository()
            repo.updateWoman(currentWoman)
            updateHeart(heartButton)
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.ic_close_button).setOnClickListener {
            // FERMETURE FENETRE POPUP
            dismiss()
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.ic_delete).setOnClickListener {
            val repo = WomanRepository()
            repo.deleteWoman(currentWoman)
            dismiss()
        }
    }

    private fun setupComponents() {
        val planetImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentWoman.imageUrl)).into(planetImage)

        findViewById<TextView>(R.id.popup_page_firstname_title).text = currentWoman.firstname

        findViewById<TextView>(R.id.popup_page_lastname_title).text = currentWoman.lastname

        findViewById<TextView>(R.id.popup_page_description_subtitle).text = currentWoman.description

        findViewById<TextView>(R.id.popup_page_activity_description).text = currentWoman.activity

        findViewById<TextView>(R.id.popup_page_nationality_description).text = currentWoman.nationality

        findViewById<TextView>(R.id.popup_page_birth_description).text = currentWoman.birth
    }

}