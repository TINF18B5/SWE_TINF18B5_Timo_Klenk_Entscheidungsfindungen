package de.dhbw.ka.inventurappprototype.gui.gegenstandstyp.bearbeiten

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp

private const val ARG_GEGENSTAND = "gegenstand"

/**
 * A simple [Fragment] subclass.
 * Use the [GegenstandstypBearbeitenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GegenstandstypBearbeitenFragment : Fragment() {
    private var gegenstand: Gegenstandstyp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gegenstand = it.getParcelable(ARG_GEGENSTAND)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gegenstandstyp_bearbeiten, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<EditText>(R.id.bearbeiten_name_gegenstandstyp).text =
            Editable.Factory.getInstance().newEditable(gegenstand?.name ?: "")

        view.findViewById<EditText>(R.id.bearbeiten_beschreibung_gegenstandstyp).text =
            Editable.Factory.getInstance().newEditable(gegenstand?.name ?: "")
    }

    companion object {

        @JvmStatic
        fun newInstance(gegenstand: Gegenstandstyp) =
            GegenstandstypBearbeitenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GEGENSTAND, gegenstand)
                }
            }
    }
}