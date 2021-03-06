package de.dhbw.ka.inventurappprototype.gui.gegenstand.liste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand


/**
 * [RecyclerView.Adapter] that can display a [Gegenstand].
 */
class GegenstandRecyclerViewAdapter(
    private val values: List<Gegenstand>,
    private val art: GegenstandsListenArt
) : RecyclerView.Adapter<GegenstandRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_gegenstand_liste, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gegenstand = values[position]
        holder.name.text = when (art) {
            is GegenstandsListenArt.LagerortVerwaltung -> gegenstand.typ.name
            is GegenstandsListenArt.GegenstandstypVerwaltung -> gegenstand.ort.name
        }

        holder.beschreibung.text = when (art) {
            is GegenstandsListenArt.LagerortVerwaltung -> gegenstand.typ.beschreibung
            is GegenstandsListenArt.GegenstandstypVerwaltung -> gegenstand.ort.beschreibung
        }
        holder.menge.text = gegenstand.menge.toString(10)
        holder.gegenstand = gegenstand
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var gegenstand: Gegenstand
        val name: TextView = view.findViewById(R.id.content_gegenstand_liste_single_name)
        val beschreibung: TextView =
            view.findViewById(R.id.content_gegenstand_liste_single_beschreibung)
        val menge: TextView = view.findViewById(R.id.content_gegenstand_liste_single_menge)

        override fun toString(): String {
            return super.toString() + " '" + beschreibung.text + "'"
        }
    }
}