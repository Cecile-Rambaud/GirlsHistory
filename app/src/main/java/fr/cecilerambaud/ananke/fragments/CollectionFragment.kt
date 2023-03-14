package fr.cecilerambaud.ananke.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.cecilerambaud.ananke.MainActivity
import fr.cecilerambaud.ananke.WomanRepository.Singleton.womanList
import fr.cecilerambaud.ananke.R
import fr.cecilerambaud.ananke.adapter.WomanAdapter
import fr.cecilerambaud.ananke.adapter.WomanItemDecoration

class CollectionFragment(
    private val context: MainActivity
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        // RECUPERATION RECYCLER VIEW
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = WomanAdapter(context, womanList.filter { it.liked }, R.layout.item_vertical_women)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(WomanItemDecoration())
        return view
    }
}