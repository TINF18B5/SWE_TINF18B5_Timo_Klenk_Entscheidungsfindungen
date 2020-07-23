package de.dhbw.ka.inventurappprototype.gui.inventur.schritt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.aktoren.EventListener
import de.dhbw.ka.inventurappprototype.daten.events.inventur.InventurBeendetEvent
import de.dhbw.ka.inventurappprototype.daten.events.inventur.NachsterInventurSchrittEvent
import de.dhbw.ka.inventurappprototype.daten.inventur.InventurSchritt
import de.dhbw.ka.inventurappprototype.daten.kommandos.inventur.InventurSchrittValidierenKommando
import de.dhbw.ka.inventurappprototype.gui.ARG_INVENTUR_SCHRITT
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import kotlinx.android.synthetic.main.fragment_inventur_schritt.*


/**
 * A simple [Fragment] subclass.
 * Use the [InventurSchrittFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventurSchrittFragment : Fragment() {
    private lateinit var schritt: InventurSchritt
    private lateinit var nachsterSchrittListener: EventListener<NachsterInventurSchrittEvent>
    private lateinit var inventurBeendetListener: EventListener<InventurBeendetEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            schritt = it.getParcelable(ARG_INVENTUR_SCHRITT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventur_schritt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_inventur_schritt_zuruck.setOnClickListener {

            findNavController().popBackStack()
        }
        updateSchritt()

        button_inventur_schritt_bestatigen.setOnClickListener {
            val menge =
                content_inventur_schritt_gegenstand_menge.text.toString().toIntOrNull()

            if (menge == null || menge < 0) {
                Snackbar.make(
                    view,
                    "Bitte eine positive Menge eingeben!",
                    BaseTransientBottomBar.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }

            when (val ergebnis = AktorenKontext.zentralerKommandoProzessor.bearbeite(
                InventurSchrittValidierenKommando(
                    nutzerName = AktorenKontext.derzeitigerNutzer.name,
                    menge = menge
                )
            )) {
                is KommandoErgebnis.NichtAkzeptiert -> Snackbar.make(
                    view,
                    ergebnis.fehler,
                    BaseTransientBottomBar.LENGTH_SHORT
                ).show()
            }
        }

        inventurBeendetListener = {
            findNavController().popBackStack()
        }

        nachsterSchrittListener = {
            schritt = it.schritt
            updateSchritt()
        }

        AktorenKontext.eventStream.register(inventurBeendetListener)
        AktorenKontext.eventStream.register(nachsterSchrittListener)

        activity?.onBackPressedDispatcher?.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

    private fun updateSchritt() {
        with(schritt.zuPruefen) {
            content_inventur_schritt_gegenstandstyp_name.text = typ.name
            content_inventur_schritt_gegenstandstyp_beschreibung.text = typ.beschreibung

            content_inventur_schritt_lagerort_name.text = ort.name
            content_inventur_schritt_lagerort_beschreibung.text = ort.beschreibung
        }

        content_inventur_schritt_gegenstand_menge.text.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        AktorenKontext.eventStream.unregister(inventurBeendetListener)
        AktorenKontext.eventStream.unregister(nachsterSchrittListener)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param inventurSchritt Der InventurSchritt
         * @return A new instance of fragment InventurSchrittFragment.
         */
        @JvmStatic
        fun newInstance(inventurSchritt: InventurSchritt) =
            InventurSchrittFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_INVENTUR_SCHRITT, inventurSchritt)
                }
            }
    }
}