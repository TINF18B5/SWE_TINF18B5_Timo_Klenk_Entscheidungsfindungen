package de.dhbw.ka.inventurappprototype.gui.gegenstand.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    private lateinit var _listenArt: GegenstandsListenArt
    var listenArt: GegenstandsListenArt
        get() = _listenArt
        set(value) {
            _listenArt = value
            val view = view
            if(view is RecyclerView){
                view.adapter = GegenstandRecyclerViewAdapter(
                    AktorenKontext.datenbankConnector.gegenstaende(listenArt),
                    listenArt
                )
            }
        }


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_LISTEN_ART = "listen-art"

        @JvmStatic
        fun newInstance(columnCount: Int, listenArt: GegenstandsListenArt) =
            GegenstandFragmentListe().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                    putParcelable(ARG_LISTEN_ART, listenArt)
                }
            }
    }
}