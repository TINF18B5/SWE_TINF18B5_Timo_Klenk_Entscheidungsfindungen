package de.dhbw.ka.inventurappprototype.gui.gegenstand.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext

/**
 * A fragment representing a list of Items.
 */
class GegenstandFragmentListe : Fragment() {

    private var columnCount = 1
    lateinit var listenArt: GegenstandsListenArt



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            listenArt = it.getParcelable(ARG_LISTEN_ART)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gegenstand_liste_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                adapter = GegenstandRecyclerViewAdapter(
                    AktorenKontext.datenbankConnector.gegenstaende(listenArt),
                    listenArt
                )

            }
        }
        return view
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_LISTEN_ART = "listen-art"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            GegenstandFragmentListe().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                    putParcelable(ARG_LISTEN_ART, listenArt)
                }
            }
    }
}