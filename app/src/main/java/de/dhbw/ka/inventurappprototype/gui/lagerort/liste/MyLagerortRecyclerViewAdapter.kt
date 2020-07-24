package de.dhbw.ka.inventurappprototype.gui.lagerort.liste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort


/**
 * [RecyclerView.Adapter] that can display a [Lagerort].
 */
class MyLagerortRecyclerViewAdapter(
    private val values: List<Lagerort>
) : RecyclerView.Adapter<MyLagerortRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lagerort_liste_single, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lagerort = values[position]
        holder.lagerortName.text = lagerort.name
        holder.lagerortBeschreibung.text = lagerort.beschreibung
        holder.lagerort = lagerort
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lagerortName: TextView = view.findViewById(R.id.content_lagerort_liste_single_name)
        val lagerortBeschreibung: TextView = view.findViewById(R.id.content_lagerort_liste_single_beschreibung)
        lateinit var lagerort: Lagerort

        override fun toString(): String {
            return super.toString() + " '" + lagerortBeschreibung.text + "'"
        }
    }
}