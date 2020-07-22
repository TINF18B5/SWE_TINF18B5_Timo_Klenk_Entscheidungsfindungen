package de.dhbw.ka.inventurappprototype.gui.gegenstandstyp.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp
import de.dhbw.ka.inventurappprototype.gui.gegenstand.liste.GegenstandFragmentListe
import de.dhbw.ka.inventurappprototype.gui.gegenstand.liste.GegenstandsListenArt

/**
 * Der Gegenstandstyp, der hier dargestellt wird
 */
const val ARG_GEGENSTANDSTYP = "gegenstandstyp"

/**
 * A simple [Fragment] subclass.
 * Use the [GegenstandstypInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GegenstandstypInfoFragment : Fragment() {

    private lateinit var gegenstandstyp: Gegenstandstyp

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            gegenstandstyp = it.getParcelable(ARG_GEGENSTANDSTYP)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_gegenstandstyp_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.bearbeiten_name).text = gegenstandstyp.name
        view.findViewById<TextView>(R.id.bearbeiten_beschreibung).text = gegenstandstyp.beschreibung
        val findFragmentByTag = childFragmentManager.findFragmentByTag("gegenstandsliste")
        val listenArt =
            GegenstandsListenArt.GegenstandstypVerwaltung(gegenstandstyp)
        if(findFragmentByTag is GegenstandFragmentListe){
            findFragmentByTag.listenArt = listenArt
        }
        view.findViewById<Button>(R.id.button_zuruck).setOnClickListener {
            findNavController().navigate(R.id.action_gegenstandstypInfoFragment_to_gegenstandstypViewFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_GEGENSTANDSTYP, gegenstandstyp)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param gegenstandstyp Parameter 1.
         * @return A new instance of fragment GegenstandstypInfoFragment.
         */
        @JvmStatic
        fun newInstance(gegenstandstyp: Gegenstandstyp) =
            GegenstandstypInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GEGENSTANDSTYP, gegenstandstyp)
                }
            }
    }
}