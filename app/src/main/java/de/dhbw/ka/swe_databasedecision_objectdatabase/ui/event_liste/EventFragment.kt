package de.dhbw.ka.swe_databasedecision_objectdatabase.ui.event_liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.dhbw.ka.swe_databasedecision_objectdatabase.R
import de.dhbw.ka.swe_databasedecision_objectdatabase.data.event.Event
import de.dhbw.ka.swe_databasedecision_objectdatabase.data.event.LagerortErstelltEvent
import de.dhbw.ka.swe_databasedecision_objectdatabase.database.DataAccess
import io.objectbox.kotlin.boxFor

/**
 * A fragment representing a list of Items.
 */
class EventFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                @Suppress("UNCHECKED_CAST") val all = DataAccess.boxStore.allEntityClasses
                    .filter(Event::class.java::isAssignableFrom)
                    .map { it as Class<Event> }
                    .flatMap { DataAccess.boxStore.boxFor(it).all };
                adapter = MyEventRecyclerViewAdapter(all)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            EventFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}