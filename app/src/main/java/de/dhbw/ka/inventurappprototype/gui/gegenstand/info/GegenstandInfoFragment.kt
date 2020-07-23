package de.dhbw.ka.inventurappprototype.gui.gegenstand.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.aktoren.EventListener
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandGeloeschtEvent
import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstand.GegenstandLoeschenKommando
import de.dhbw.ka.inventurappprototype.gui.*
import de.dhbw.ka.inventurappprototype.gui.gegenstand.liste.GegenstandsListenArt
import de.dhbw.ka.inventurappprototype.gui.gegenstand.umlagern.UmlagerRichtung
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import kotlinx.android.synthetic.main.fragment_gegenstand_info.*

/**
 * A simple [Fragment] subclass.
 * Use the [GegenstandInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GegenstandInfoFragment : Fragment() {
    private lateinit var gegenstand: Gegenstand
    private lateinit var listenArt: GegenstandsListenArt

    private lateinit var geloschtListener: EventListener<GegenstandGeloeschtEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gegenstand = it.getParcelable(ARG_GEGENSTAND)!!
            listenArt = it.getParcelable(ARG_LISTEN_ART)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gegenstand_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        content_gegenstand_info_gegenstandstyp_beschreibung.text =
            gegenstand.typ.name
        content_gegenstand_info_gegenstandstyp_name.text =
            gegenstand.typ.beschreibung
        content_gegenstand_info_lagerort_beschreibung.text =
            gegenstand.ort.name
        content_gegenstand_info_lagerort_name.text =
            gegenstand.ort.beschreibung

        content_gegenstand_info_menge.text =
            gegenstand.menge.toString()

        button_gegenstand_info_einlagern.setOnClickListener {
            findNavController().navigate(
                R.id.action_gegenstandInfoFragment_to_gegenstandUmlagernFragment,
                bundleOf(
                    ARG_GEGENSTAND to gegenstand,
                    ARG_RICHTUNG to UmlagerRichtung.EINLAGERN
                )
            )
        }

        button_gegenstand_info_auslagern.setOnClickListener {
            findNavController().navigate(
                R.id.action_gegenstandInfoFragment_to_gegenstandUmlagernFragment,
                bundleOf(
                    ARG_GEGENSTAND to gegenstand,
                    ARG_RICHTUNG to UmlagerRichtung.AUSLAGERN
                )
            )
        }

        button_gegenstand_info_zuruck.setOnClickListener {
            val bundle = Bundle()
            val id: Int = when (val tempListe = listenArt) {
                is GegenstandsListenArt.LagerortVerwaltung -> {
                    bundle.putParcelable(ARG_LAGERORT, tempListe.lagerort)
                    R.id.action_gegenstandInfoFragment_to_lagerortInfoFragment
                }
                is GegenstandsListenArt.GegenstandstypVerwaltung -> {
                    bundle.putParcelable(ARG_GEGENSTANDSTYP, tempListe.gegenstandstyp)
                    R.id.action_gegenstandInfoFragment_to_gegenstandstypInfoFragment
                }
            }
            findNavController().navigate(id, bundle)
        }

        button_gegenstand_info_loeschen.setOnClickListener {
            val kommando = GegenstandLoeschenKommando(
                nutzerName = AktorenKontext.derzeitigerNutzer.name,
                GegenstandstypID = gegenstand.typ.ID,
                lagerortName = gegenstand.ort.name,
                grund = input_gegenstand_info_loeschen_grund.text.toString()
            )

            when (val ergebnis = AktorenKontext.zentralerKommandoProzessor.bearbeite(kommando)) {
                is KommandoErgebnis.NichtAkzeptiert -> Snackbar.make(view, ergebnis.fehler, 10)
                    .show()
            }
        }

        geloschtListener = {
            val bundle = Bundle()
            val id: Int = when (val tempListe = listenArt) {
                is GegenstandsListenArt.LagerortVerwaltung -> {
                    bundle.putParcelable(ARG_LAGERORT, tempListe.lagerort)
                    R.id.action_gegenstandInfoFragment_to_lagerortInfoFragment
                }
                is GegenstandsListenArt.GegenstandstypVerwaltung -> {
                    bundle.putParcelable(ARG_GEGENSTANDSTYP, tempListe.gegenstandstyp)
                    R.id.action_gegenstandInfoFragment_to_gegenstandstypInfoFragment
                }
            }
            findNavController().navigate(id, bundle)
        }
        AktorenKontext.eventStream.register(geloschtListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        AktorenKontext.eventStream.unregister(geloschtListener)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param gegenstand Der benutzte Gegenstand.
         * @return A new instance of fragment GegenstandInfoFragment.
         */
        @JvmStatic
        fun newInstance(gegenstand: Gegenstand) =
            GegenstandInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GEGENSTAND, gegenstand)
                }
            }
    }
}