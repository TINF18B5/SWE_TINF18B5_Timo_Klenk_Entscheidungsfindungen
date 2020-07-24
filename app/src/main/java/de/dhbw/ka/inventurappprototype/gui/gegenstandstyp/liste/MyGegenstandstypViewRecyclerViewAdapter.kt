package de.dhbw.ka.inventurappprototype.gui.gegenstandstyp.liste

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.dhbw.ka.inventurappprototype.R
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp


/**
 * [RecyclerView.Adapter] that can display a [Gegenstandstyp].
 */
class MyGegenstandstypViewRecyclerViewAdapter(
    private val values: List<Gegenstandstyp>
) : RecyclerView.Adapter<MyGegenstandstypViewRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_gegenstandstyp_single, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        holder.beschreibung.text = item.beschreibung
        holder.gegenstandstyp = item
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.content_gegenstandstyp_liste_single_name)
        val beschreibung: TextView = view.findViewById(R.id.content_gegenstandstyp_liste_single_beschreibung)
        lateinit var gegenstandstyp: Gegenstandstyp

        override fun toString(): String {
            return super.toString() + " '" + beschreibung.text + "'"
        }
    }
}