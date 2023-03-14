package fr.cecilerambaud.ananke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.widget.CheckBox




class RegisterActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth= FirebaseAuth.getInstance()

    }

    fun register(view: View){
        val email = findViewById<EditText>(R.id.editTextEmailAddress).text.toString()
        val password=findViewById<EditText>(R.id.editTextPassword).text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }
    fun goToLogin(view: View){
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}