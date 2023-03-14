package fr.cecilerambaud.ananke.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.cecilerambaud.ananke.MainActivity
import fr.cecilerambaud.ananke.R
import fr.cecilerambaud.ananke.WomanRepository
import fr.cecilerambaud.ananke.adapter.WomanAdapter
import fr.cecilerambaud.ananke.adapter.WomanItemDecoration


class SeeAllWoman(private val context: MainActivity): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater?.inflate(R.layout.fragment_see_all, container, false)

        val seeAllRecyclerView = view.findViewById<RecyclerView>(R.id.see_all_recycler_view)
        seeAllRecyclerView.adapter = WomanAdapter(context, WomanRepository.Singleton.womanList, R.layout.item_see_all_woman)
        seeAllRecyclerView.addItemDecoration(WomanItemDecoration())


        return view

    }

}





