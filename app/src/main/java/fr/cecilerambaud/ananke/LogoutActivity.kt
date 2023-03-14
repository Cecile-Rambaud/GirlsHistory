package fr.cecilerambaud.ananke

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LogoutActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth= FirebaseAuth.getInstance()

    }
    fun userLogout(){
        FirebaseAuth.getInstance().signOut()
        finish()
    }
}