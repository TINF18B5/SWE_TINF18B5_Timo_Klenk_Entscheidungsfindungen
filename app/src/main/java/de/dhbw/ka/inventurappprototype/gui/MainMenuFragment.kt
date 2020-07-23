package de.dhbw.ka.inventurappprototype.gui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.dhbw.ka.inventurappprototype.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_main_menu_inventur).setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_inventurLagerortWahlFragment)
        }

        view.findViewById<Button>(R.id.button_main_menu_gegenstandstypliste).setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_gegenstandstypViewFragment)
        }

        view.findViewById<Button>(R.id.button_main_menu_lagerortliste).setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_lagerortListeFragment)
        }
    }
}