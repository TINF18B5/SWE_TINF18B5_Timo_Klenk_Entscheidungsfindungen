package de.dhbw.ka.inventurappprototype.gui.gegenstandstyp.bearbeiten

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.aktoren.EventListener
import de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp.GegenstandstypBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp.GegenstandstypErstelltEvent
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.AbstractGegenstandstypKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.GegenstandstypBearbeitenKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.GegenstandstypErstellenKommando
import de.dhbw.ka.inventurappprototype.gui.ARG_GEGENSTANDSTYP
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis


/**
 * A simple [Fragment] subclass.
 * Use the [GegenstandstypBearbeitenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GegenstandstypBearbeitenFragment : Fragment() {
    private var gegenstand: Gegenstandstyp? = null
    private lateinit var bearbeitetListener: EventListener<GegenstandstypBearbeitetEvent>
    private lateinit var erstelltListener: EventListener<GegenstandstypErstelltEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gegenstand = it.getParcelable(ARG_GEGENSTANDSTYP)
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
        val nameContent = view.findViewById<EditText>(R.id.content_gegenstandstyp_bearbeiten_name)
        nameContent.text =
            Editable.Factory.getInstance().newEditable(gegenstand?.name ?: "")

        val beschreibungContent =
            view.findViewById<EditText>(R.id.content_gegenstandstyp_bearbeiten_beschreibung)
        beschreibungContent.text =
            Editable.Factory.getInstance().newEditable(gegenstand?.beschreibung ?: "")

        view.findViewById<Button>(R.id.button_gegenstandstyp_bearbeiten_zuruck).setOnClickListener {
            if (gegenstand == null) {
                findNavController().navigate(
                    R.id.action_gegenstandstypBearbeitenFragment_to_gegenstandstypViewFragment
                )
            } else {
                findNavController().navigate(
                    R.id.action_gegenstandstypBearbeitenFragment_to_gegenstandstypInfoFragment,
                    bundleOf(ARG_GEGENSTANDSTYP to gegenstand)
                )
            }
        }

        erstelltListener = {
            val findNavController = findNavController()
            if (findNavController.currentDestination?.id != R.id.gegenstandstypInfoFragment) {
                findNavController.navigate(
                    R.id.action_gegenstandstypBearbeitenFragment_to_gegenstandstypInfoFragment,
                    bundleOf(
                        ARG_GEGENSTANDSTYP to AktorenKontext.datenbankConnector.gegenstandstyp(
                            it.ID
                        )
                    )
                )
            }
        }

        bearbeitetListener = {
            val findNavController = findNavController()
            if (findNavController.currentDestination?.id != R.id.gegenstandstypInfoFragment) {
                findNavController.navigate(
                    R.id.action_gegenstandstypBearbeitenFragment_to_gegenstandstypInfoFragment,
                    bundleOf(
                        ARG_GEGENSTANDSTYP to AktorenKontext.datenbankConnector.gegenstandstyp(
                            it.ID
                        )
                    )
                )
            }
        }
        AktorenKontext.eventStream.register(erstelltListener)
        AktorenKontext.eventStream.register(bearbeitetListener)

        view.findViewById<Button>(R.id.button_gegenstandstyp_bearbeiten_speichern)
            .setOnClickListener {


                val beschreibung = beschreibungContent.text.toString()
                val name = nameContent.text.toString()
                val kommando: AbstractGegenstandstypKommando =
                    when (val tempGegenstandstyp = gegenstand) {
                        null -> GegenstandstypErstellenKommando(
                            nutzerName = AktorenKontext.derzeitigerNutzer.name,
                            beschreibung = beschreibung,
                            name = name,
                            erlaubeDoppeltenNamen = true //TODO: Ersetze mit UI Element
                        )
                        else -> GegenstandstypBearbeitenKommando(
                            nutzerName = AktorenKontext.derzeitigerNutzer.name,
                            neueBeschreibung = beschreibung,
                            neuerName = name,
                            ID = tempGegenstandstyp.ID
                        )
                    }

                when (val ergebnis =
                    AktorenKontext.zentralerKommandoProzessor.bearbeite(kommando)) {
                    is KommandoErgebnis.NichtAkzeptiert ->
                        Snackbar.make(view, ergebnis.fehler, BaseTransientBottomBar.LENGTH_SHORT)
                            .show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        AktorenKontext.eventStream.unregister(erstelltListener)
        AktorenKontext.eventStream.unregister(bearbeitetListener)
    }

    companion object {

        @JvmStatic
        fun newInstance(gegenstandstyp: Gegenstandstyp) =
            GegenstandstypBearbeitenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GEGENSTANDSTYP, gegenstandstyp)
                }
            }
    }
}