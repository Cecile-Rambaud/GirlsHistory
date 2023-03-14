package fr.cecilerambaud.ananke

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.cecilerambaud.ananke.WomanRepository.Singleton.databaseRef
import fr.cecilerambaud.ananke.WomanRepository.Singleton.downloadUri
import fr.cecilerambaud.ananke.WomanRepository.Singleton.storageReference
import fr.cecilerambaud.ananke.WomanRepository.Singleton.womanList
import java.net.URI
import java.util.*

class WomanRepository {

    object Singleton {
        // LIEN DU BUCKET
        private val BUCKET_URL: String = "gs://ananke-3c888.appspot.com"

        // CONNEXION ESPACE DE STOCKAGE
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // CONNEXION A LA REFERENCE "women" DE LA BDD
        val databaseRef = FirebaseDatabase.getInstance().getReference("women")

        // CREATION DE LA LISTE CONTENANT LES PERSONNALITES
        val womanList = arrayListOf<WomanModel>()

        // LIEN IMAGE COURANTE
        var downloadUri: Uri? = null
    }
    fun updateData(callback: () -> Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                womanList.clear()
                for (ds in snapshot.children) {
                    val woman = ds.getValue(WomanModel::class.java)
                    if(woman != null) {
                        womanList.add(woman)
                    }
                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    fun uploadImage(file: Uri, callback: () -> Unit) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = storageReference.child(fileName)
        val uploadTask = ref.putFile(file)

        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if(!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                downloadUri = task.result
                callback()
            }
        }
    }

    fun updateWoman(woman: WomanModel) = databaseRef.child(woman.id).setValue(woman)

    fun insertWoman(woman: WomanModel) = databaseRef.child(woman.id).setValue(woman)

    fun deleteWoman(woman: WomanModel) = databaseRef.child(woman.id).removeValue()
}