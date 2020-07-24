package de.dhbw.ka.inventurappprototype.gui.inventur.selektion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.aktoren.EventListener
import de.dhbw.ka.inventurappprototype.daten.events.inventur.InventurBeendetEvent
import de.dhbw.ka.inventurappprototype.daten.events.inventur.NachsterInventurSchrittEvent
import de.dhbw.ka.inventurappprototype.daten.inventur.Inventur
import de.dhbw.ka.inventurappprototype.daten.kommandos.inventur.InventurStartenKommando
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort
import de.dhbw.ka.inventurappprototype.gui.ARG_INVENTUR_SCHRITT
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import kotlinx.android.synthetic.main.fragment_inventur_lagerort_wahl_list.*

/**
 * A fragment representing a list of Items.
 */
class InventurLagerortWahlFragment : Fragment() {

    private var columnCount = 1
    private lateinit var nachsterSchrittListener: EventListener<NachsterInventurSchrittEvent>

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
        val view = inflater.inflate(R.layout.fragment_inventur_lagerort_wahl_list, container, false)

        val rView = view.findViewById<RecyclerView>(R.id.recycler_inventur_lagerortauswahl_orte)
        // Set the adapter
        with(rView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter =
                MyLagerortWahlRecyclerViewAdapter(AktorenKontext.datenbankConnector.lagerorte.toList())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_inventur_lagerortauswahl_zuruck.setOnClickListener {
            findNavController().popBackStack()
        }

        button_inventur_lagerortauswahl_start.setOnClickListener {
            val rView = view.findViewById<RecyclerView>(R.id.recycler_inventur_lagerortauswahl_orte)
            val inventur = when (val adapter = rView.adapter) {
                is MyLagerortWahlRecyclerViewAdapter -> Inventur(adapter.lagerorteInInventur.toList())
                else -> return@setOnClickListener
            }

            val kommando = InventurStartenKommando(AktorenKontext.derzeitigerNutzer.name, inventur)

            when (val ergebnis = AktorenKontext.zentralerKommandoProzessor.bearbeite(kommando)) {
                is KommandoErgebnis.NichtAkzeptiert -> Snackbar.make(view, ergebnis.fehler, BaseTransientBottomBar.LENGTH_SHORT)
                    .show()
            }
        }

        nachsterSchrittListener = {
            val findNavController = findNavController()
            if(findNavController.currentDestination?.id != R.id.inventurSchrittFragment) {
                findNavController.navigate(
                    R.id.action_inventurLagerortWahlFragment_to_inventurSchrittFragment,
                    bundleOf(ARG_INVENTUR_SCHRITT to it.schritt)
                )
            }
        }


        AktorenKontext.eventStream.register(nachsterSchrittListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        AktorenKontext.eventStream.unregister(nachsterSchrittListener)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            InventurLagerortWahlFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}