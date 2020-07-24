package de.dhbw.ka.inventurappprototype.gui.lagerort.bearbeiten

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.aktoren.EventListener
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortErstelltEvent
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.LagerortBearbeitenKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.LagerortErstellenKommando
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort
import de.dhbw.ka.inventurappprototype.gui.ARG_LAGERORT
import de.dhbw.ka.inventurappprototype.gui.lagerort.info.LagerortInfoFragment
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import kotlinx.android.synthetic.main.fragment_lagerort_bearbeiten.*


/**
 * A simple [Fragment] subclass.
 * Use the [LagerortBearbeitenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LagerortBearbeitenFragment : Fragment() {
    private var lagerort: Lagerort? = null
    private lateinit var bearbeitetListener: EventListener<LagerortBearbeitetEvent>
    private lateinit var erstelltListener: EventListener<LagerortErstelltEvent>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_lagerort_bearbeiten_zuruck.setOnClickListener {
            findNavController().popBackStack()
        }

        content_lagerort_bearbeiten_name.text =
            Editable.Factory.getInstance().newEditable(lagerort?.name ?: "")

        content_lagerort_bearbeiten_beschreibung.text =
            Editable.Factory.getInstance().newEditable(lagerort?.beschreibung ?: "")

        button_lagerort_bearbeiten_speichern.setOnClickListener {
            val kommando = when (val tempLagerort = lagerort) {
                null -> LagerortErstellenKommando(
                    nutzerName = AktorenKontext.derzeitigerNutzer.name,
                    name = content_lagerort_bearbeiten_name.text.toString(),
                    beschreibung = content_lagerort_bearbeiten_beschreibung.text.toString()
                )
                else -> LagerortBearbeitenKommando(
                    nutzerName = AktorenKontext.derzeitigerNutzer.name,
                    name = tempLagerort.name,
                    neuerName = content_lagerort_bearbeiten_name.text.toString(),
                    neueBeschreibung = content_lagerort_bearbeiten_beschreibung.text.toString()
                )
            }

            when (val ergebnis =
                AktorenKontext.zentralerKommandoProzessor.bearbeite(kommando)) {
                is KommandoErgebnis.NichtAkzeptiert ->
                    Snackbar.make(view, ergebnis.fehler, BaseTransientBottomBar.LENGTH_SHORT).show()
            }
        }

        erstelltListener = {
            findNavController().navigate(
                R.id.action_lagerortBearbeitenFragment_to_lagerortInfoFragment,
                bundleOf(ARG_LAGERORT to AktorenKontext.datenbankConnector.lagerort(it.name))
            )
        }

        bearbeitetListener = {
            findNavController().popBackStack()
            when(val last = parentFragmentManager.fragments.last()) {
                is LagerortInfoFragment -> {
                    last.lagerort = Lagerort(it.neuerName, it.neueBeschreibung)
                }
            }
        }
        AktorenKontext.eventStream.register(erstelltListener)
        AktorenKontext.eventStream. register(bearbeitetListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        AktorenKontext.eventStream.unregister(erstelltListener)
        AktorenKontext.eventStream.unregister(bearbeitetListener)
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