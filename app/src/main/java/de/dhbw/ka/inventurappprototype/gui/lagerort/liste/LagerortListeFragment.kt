package de.dhbw.ka.inventurappprototype.gui.lagerort.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.gui.ARG_GEGENSTANDSTYP
import de.dhbw.ka.inventurappprototype.gui.ARG_LAGERORT
import de.dhbw.ka.inventurappprototype.gui.gegenstandstyp.liste.MyGegenstandstypViewRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_lagerort_liste_gesamt.*

/**
 * A fragment representing a list of Items.
 */
class LagerortListeFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_lagerort_liste_gesamt, container, false)

        val rView = view.findViewById<RecyclerView>(R.id.recycler_lagerort_liste)
        // Set the adapter

        with(rView) {
            addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean =
                    true

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                    val findChildViewUnder = rv.findChildViewUnder(e.x, e.y) ?: return
                    val childViewHolder = rv.getChildViewHolder(findChildViewUnder)
                    if (childViewHolder is MyLagerortRecyclerViewAdapter.ViewHolder) {
                        val findNavController = view.findNavController()
                        if (findNavController.currentDestination?.id != R.id.lagerortInfoFragment) {
                            findNavController.navigate(
                                R.id.action_lagerortListeFragment_to_lagerortInfoFragment,
                                bundleOf(ARG_LAGERORT to childViewHolder.lagerort)
                            )
                        }
                    }
                }
            })

            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyLagerortRecyclerViewAdapter(AktorenKontext.datenbankConnector.lagerorte.toList())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_lagerort_liste_gesamt_zuruck.setOnClickListener {
            findNavController().popBackStack()
        }

        button_lagerort_liste_gesamt_sortieren.setOnClickListener {
            Snackbar.make(view, R.string.label_nicht_implementiert, BaseTransientBottomBar.LENGTH_SHORT).show()
        }

        button_lagerort_liste_gesamt_erstellen.setOnClickListener {
            val findNavController = findNavController()
            if(findNavController.currentDestination?.id != R.id.lagerortBearbeitenFragment) {
                findNavController.navigate(R.id.action_lagerortListeFragment_to_lagerortBearbeitenFragment)
            }
        }
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            LagerortListeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}