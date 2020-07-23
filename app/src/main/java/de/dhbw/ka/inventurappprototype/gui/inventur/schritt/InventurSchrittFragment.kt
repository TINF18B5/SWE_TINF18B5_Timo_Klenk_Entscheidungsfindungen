package de.dhbw.ka.inventurappprototype.gui.inventur.schritt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.inventur.InventurSchritt

private const val ARG_SCHRITT = "inventur_schritt"

/**
 * A simple [Fragment] subclass.
 * Use the [InventurSchrittFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventurSchrittFragment : Fragment() {
    private lateinit var schritt: InventurSchritt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            schritt = it.getParcelable(ARG_SCHRITT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventur_schritt, container, false)
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
                    putParcelable(ARG_SCHRITT, inventurSchritt)
                }
            }
    }
}