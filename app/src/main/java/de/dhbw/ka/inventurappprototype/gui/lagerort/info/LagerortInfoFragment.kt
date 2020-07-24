package de.dhbw.ka.inventurappprototype.gui.lagerort.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort
import de.dhbw.ka.inventurappprototype.gui.ARG_GEGENSTAND
import de.dhbw.ka.inventurappprototype.gui.ARG_LAGERORT
import de.dhbw.ka.inventurappprototype.gui.ARG_RICHTUNG
import de.dhbw.ka.inventurappprototype.gui.gegenstand.liste.GegenstandFragmentListe
import de.dhbw.ka.inventurappprototype.gui.gegenstand.liste.GegenstandsListenArt
import de.dhbw.ka.inventurappprototype.gui.gegenstand.umlagern.UmlagerRichtung
import kotlinx.android.synthetic.main.fragment_lagerort_info.*


/**
 * A simple [Fragment] subclass.
 * Use the [LagerortInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LagerortInfoFragment : Fragment() {
    private lateinit var _lagerort: Lagerort
    var lagerort: Lagerort
        get() = _lagerort
        set(value) {
            _lagerort = value
            if (view != null) {
                content_lagerort_info_name.text = lagerort.name
                content_lagerort_info_beschreibung.text = lagerort.beschreibung
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _lagerort = it.getParcelable(ARG_LAGERORT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lagerort_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gegenstandliste =
            childFragmentManager.findFragmentByTag("container_lagerortinfo_gegenstandliste")
        if (gegenstandliste is GegenstandFragmentListe) {
            gegenstandliste.listenArt = GegenstandsListenArt.LagerortVerwaltung(lagerort)
        }

        content_lagerort_info_name.text = lagerort.name
        content_lagerort_info_beschreibung.text = lagerort.beschreibung

        button_lagerort_info_zuruck.setOnClickListener {
            findNavController().popBackStack()
        }

        button_lagerort_info_bearbeiten.setOnClickListener {
            findNavController().navigate(
                R.id.action_lagerortInfoFragment_to_lagerortBearbeitenFragment,
                bundleOf(ARG_LAGERORT to lagerort)
            )
        }

        button_lagerort_info_erstellen.setOnClickListener {
            findNavController().navigate(
                R.id.action_lagerortInfoFragment_to_gegenstandUmlagernFragment,
                bundleOf(
                    ARG_RICHTUNG to UmlagerRichtung.ERSTMALIGES_EINLAGERN,
                    ARG_GEGENSTAND to Gegenstand(Gegenstandstyp.none, lagerort, 0)
                )
            )
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param lagerort Der Lagerort.
         * @return A new instance of fragment LagerortInfoFragment.
         */
        @JvmStatic
        fun newInstance(lagerort: Lagerort) =
            LagerortInfoFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_LAGERORT, lagerort)
                    }
                }
    }
}