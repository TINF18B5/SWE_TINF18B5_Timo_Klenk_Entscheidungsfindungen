package de.dhbw.ka.inventurappprototype.gui.gegenstand.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand

private const val ARG_GEGENSTAND = "gegenstand"

/**
 * A simple [Fragment] subclass.
 * Use the [GegenstandInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GegenstandInfoFragment : Fragment() {
    private lateinit var gegenstand: Gegenstand

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gegenstand = it.getParcelable(ARG_GEGENSTAND)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gegenstand_info, container, false)
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