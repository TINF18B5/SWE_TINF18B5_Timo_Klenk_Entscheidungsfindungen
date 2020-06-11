package de.dhbw.ka.swe_databasedecision_objectdatabase.ui.lagerort_liste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.dhbw.ka.swe_databasedecision_objectdatabase.R
import de.dhbw.ka.swe_databasedecision_objectdatabase.data.Lagerort


/**
 * [RecyclerView.Adapter] that can display a [Lagerort].
 * TODO: Replace the implementation with code for your data type.
 */
class MyLagerortRecyclerViewAdapter(
    private val values: List<Lagerort>
) : RecyclerView.Adapter<MyLagerortRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.name
        holder.contentView.text = item.beschreibung
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}