package de.dhbw.ka.inventurappprototype.gui.gegenstand.umlagern

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand

private const val ARG_GEGENSTAND = "gegenstand"
private const val ARG_RICHTUNG = "richtung"

/**
 * A simple [Fragment] subclass.
 * Use the [GegenstandUmlagernFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GegenstandUmlagernFragment : Fragment() {
    private var gegenstand: Gegenstand? = null
    private var richtung: UmlagerRichtung = UmlagerRichtung.EINLAGERN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gegenstand = it.getParcelable(ARG_GEGENSTAND)
            richtung = it.getSerializable(ARG_RICHTUNG) as UmlagerRichtung
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gegenstand_umlagern, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param gegenstand Der Gegenstand.
         * @param richtung Die Richtung
         * @return A new instance of fragment GegenstandUmlagernFragment.
         */
        @JvmStatic
        fun newInstance(gegenstand: Gegenstand, richtung: UmlagerRichtung = UmlagerRichtung.EINLAGERN) =
            GegenstandUmlagernFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GEGENSTAND, gegenstand)
                    putSerializable(ARG_RICHTUNG, richtung)
                }
            }
    }
}