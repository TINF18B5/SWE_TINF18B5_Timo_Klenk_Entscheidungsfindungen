package de.dhbw.ka.inventurappprototype.gui.lagerort.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort

private const val ARG_LAGERORT = "lagerort"

/**
 * A simple [Fragment] subclass.
 * Use the [LagerortInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LagerortInfoFragment : Fragment() {
    private lateinit var lagerort: Lagerort

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lagerort = it.getParcelable(ARG_LAGERORT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lagerort_info, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param lagerort Der Lagerort.
         * @return A new instance of fragment LagerortInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
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