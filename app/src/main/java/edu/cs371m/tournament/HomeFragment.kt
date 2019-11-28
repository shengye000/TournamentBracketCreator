package edu.cs371m.tournament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class HomeFragment: Fragment() {

    data class Bracket(val p1 : String, val score1 : Int, val p2: String, val score2: Int)

    private val bracket = listOf(Bracket("Player 1", 3, "Player 2", 2), Bracket("Player 3", 0, "Player 4", 3), Bracket("Player 5",0,"Player 6",0))

    private lateinit var rootView: View

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(bracket)
        }
    }
}