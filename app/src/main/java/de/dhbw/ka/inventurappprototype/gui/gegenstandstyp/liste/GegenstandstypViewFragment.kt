package de.dhbw.ka.inventurappprototype.gui.gegenstandstyp.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.gui.gegenstandstyp.info.ARG_GEGENSTANDSTYP

/**
 * A fragment representing a list of Items.
 */
class GegenstandstypViewFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gegenstandstyp_list, container, false)

        // Set the adapter
        val rView = view.findViewById<RecyclerView>(R.id.gegenstandstyp_liste_recycler)
        if (rView is RecyclerView) {
            with(rView) {
                addOnItemTouchListener(object : SimpleOnItemTouchListener() {
                    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean =
                        true

                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                        val findChildViewUnder = rv.findChildViewUnder(e.x, e.y) ?: return
                        val childViewHolder = rv.getChildViewHolder(findChildViewUnder)
                        if (childViewHolder is MyGegenstandstypViewRecyclerViewAdapter.ViewHolder) {
                            val bundle = Bundle()
                            bundle.putParcelable(ARG_GEGENSTANDSTYP, childViewHolder.gegenstandstyp)


                            val findNavController = view.findNavController()
                            if(findNavController.currentDestination?.id != R.id.gegenstandstypInfoFragment) {
                                findNavController.navigate(
                                    R.id.action_gegenstandstypViewFragment_to_gegenstandstypInfoFragment,
                                    bundle
                                )
                            }
                        }
                    }
                })
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                    MyGegenstandstypViewRecyclerViewAdapter(
                        AktorenKontext.datenbankConnector.gegenstandsTypen
                    )
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            GegenstandstypViewFragment()
                .apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}