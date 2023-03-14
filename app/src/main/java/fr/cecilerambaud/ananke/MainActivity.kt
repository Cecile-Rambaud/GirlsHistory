package fr.cecilerambaud.ananke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.cecilerambaud.ananke.fragments.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       loadFragment(HomeFragment(this), R.string.home_page_title)

        // IMPORTATION DU BOTTOM NAVIGATION VIEW
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.see_all -> {
                    loadFragment(SeeAllWoman(this), R.string.see_all_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_woman -> {
                    loadFragment(AddWomanFragment(this), R.string.add_woman_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
               // R.id.user_profile -> {
                //    loadFragment(UserProfileFragment(this), R.string.user_profile_page_title)
                 //   return@setOnNavigationItemSelectedListener true
              //  }
                else -> false
            }
        }

    }
    private fun loadFragment(fragment: Fragment, string: Int) {
        // CHARGER LE REPOSITORY
        val repo = WomanRepository()

        // ACTUALISATION DU TITRE
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)

        // METTRE A JOUR LA LISTE DE FORMATIONS
        repo.updateData {
            // INJECTION DU FRAGMENT DANS LA BOITE
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}


