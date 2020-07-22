package de.dhbw.ka.inventurappprototype.gui.lagerort.bearbeiten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort

private const val ARG_LAGERORT = "lagerort"

/**
 * A simple [Fragment] subclass.
 * Use the [LagerortBearbeitenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LagerortBearbeitenFragment : Fragment() {
    private var lagerort: Lagerort? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lagerort = it.getParcelable(ARG_LAGERORT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lagerort_bearbeiten, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param lagerort der Lagerort, oder <c>null</c> wenn einer erstellt werden soll.
         * @return A new instance of fragment LagerortBearbeitenFragment.
         */
        @JvmStatic
        fun newInstance(lagerort: Lagerort) =
            LagerortBearbeitenFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_LAGERORT, lagerort)
                    }
                }
    }
}