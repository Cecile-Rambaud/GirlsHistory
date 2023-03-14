package fr.cecilerambaud.ananke.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.cecilerambaud.ananke.*

class WomanAdapter(
    val context: MainActivity,
    private val womanList: List<WomanModel>,
    private val layoutId: Int
) : RecyclerView.Adapter<WomanAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val womanImage = view.findViewById<ImageView>(R.id.image_item)
        val womanFistName:TextView? = view.findViewById(R.id.first_name_item)
        val womanLastName:TextView? = view.findViewById(R.id.last_name_item)
        val womanActivity:TextView?  = view.findViewById(R.id.activity_item)
        val womanNationality:TextView? = view.findViewById(R.id.nationality_item)
        val womanDescription:TextView?  = view.findViewById(R.id.description_item)
        val womanBirth:TextView?  = view.findViewById(R.id.birth_item)
        val favoriteIcon: ImageView? = view.findViewById(R.id.heart_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWoman = womanList[position]

        val repo = WomanRepository()

        Glide.with(context).load(Uri.parse(currentWoman.imageUrl)).into(holder.womanImage)

        holder.womanFistName?.text = currentWoman.firstname

        holder.womanLastName?.text = currentWoman.lastname

        holder.womanActivity?.text = currentWoman.activity

        holder.womanNationality?.text = currentWoman.nationality

        holder.womanDescription?.text = currentWoman.description

        holder.womanBirth?.text = currentWoman.birth

        if(currentWoman.liked) {
            holder.favoriteIcon?.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.favoriteIcon?.setImageResource(R.drawable.ic_unfavorite)
        }
        holder.favoriteIcon?.setOnClickListener {
            currentWoman.liked = !currentWoman.liked
            repo.updateWoman(currentWoman)
        }
        holder.itemView.setOnClickListener {
            // AFFICHAGE DE LA POPUP
            WomanPopup(this, currentWoman).show()
        }
    }

    override fun getItemCount(): Int = womanList.size
}