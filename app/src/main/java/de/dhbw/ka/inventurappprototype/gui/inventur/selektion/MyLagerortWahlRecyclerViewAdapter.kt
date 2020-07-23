package de.dhbw.ka.inventurappprototype.gui.inventur.selektion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort


/**
 * [RecyclerView.Adapter] that can display a [Lagerort].
 */
class MyLagerortWahlRecyclerViewAdapter(
    private val values: List<Lagerort>
) : RecyclerView.Adapter<MyLagerortWahlRecyclerViewAdapter.ViewHolder>() {

    val lagerorteInInventur = mutableSetOf<Lagerort>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_inventur_lagerort_wahl_einzeln, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lagerort = values[position]
        holder.lagerortName.text = lagerort.name
        holder.lagerortBeschreibung.text = lagerort.beschreibung
        holder.itemView.findViewById<CheckBox>(R.id.checkbox_inventur_lagerortauswahl_einzeln)
            .setOnCheckedChangeListener { _, isChecked ->
                when {
                    isChecked -> lagerorteInInventur.add(lagerort)
                    else -> lagerorteInInventur.remove(lagerort)
                }
            }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lagerortName: TextView =
            view.findViewById(R.id.content_inventur_lagerortauswahl_einzeln_name)
        val lagerortBeschreibung: TextView =
            view.findViewById(R.id.content_inventur_lagerortauswahl_einzeln_beschreibung)

        override fun toString(): String {
            return super.toString() + " '" + lagerortBeschreibung.text + "'"
        }
    }
}