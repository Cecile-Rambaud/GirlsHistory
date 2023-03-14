package fr.cecilerambaud.ananke.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.cecilerambaud.ananke.MainActivity
import fr.cecilerambaud.ananke.WomanRepository.Singleton.womanList
import fr.cecilerambaud.ananke.R
import fr.cecilerambaud.ananke.adapter.WomanAdapter
import fr.cecilerambaud.ananke.adapter.WomanItemDecoration

class HomeFragment(
    private val context: MainActivity
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = WomanAdapter(context, womanList.filter { !it.liked }, R.layout.item_horizontal_women)

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = WomanAdapter(context, womanList, R.layout.item_vertical_women)
        verticalRecyclerView.addItemDecoration(WomanItemDecoration())

        return view
    }
}