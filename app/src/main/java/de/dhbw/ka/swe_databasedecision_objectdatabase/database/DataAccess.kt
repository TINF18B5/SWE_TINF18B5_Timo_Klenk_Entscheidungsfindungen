package de.dhbw.ka.swe_databasedecision_objectdatabase.database

import android.content.Context
import de.dhbw.ka.swe_databasedecision_objectdatabase.data.Lagerort
import de.dhbw.ka.swe_databasedecision_objectdatabase.data.MyObjectBox
import de.dhbw.ka.swe_databasedecision_objectdatabase.data.event.LagerortErstelltEvent
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import java.util.*

object DataAccess {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        if (this::boxStore.isInitialized) return
        boxStore = MyObjectBox.builder().androidContext(context).build()

        // Auskommentiert, da hierbei die Persistenz zwischen Starts getestet wurde.
        // Muss daher für diesen Fall nur beim ersten Mal benutzt werden, um die Datenbasis initial zu füllen.
        //erstelleDatenbankInhalt();
    }

    /**
     * Erstellt manuell Lagerorte, da noch keine Möglichkeit erstellt wurde, in der App das zu tun.
     * Würde im Produktiv-Code natürlich rausfallen, es ging hier erst einmal um das Persistieren.
     */
    private fun erstelleDatenbankInhalt() {
        //Erst mal alles löschen, um vergleichbare Datenbasis zu besitzen
        boxStore.removeAllObjects()

        val boxFor: Box<Lagerort> = boxStore.boxFor()
        boxFor.removeAll()
        boxFor.put(
            (0 until 10).map {
                Lagerort(
                    "Nummer $it",
                    "Das ist Lagerort $it, pass gut auf ihn auf!",
                    0
                )
            }
        )

        val createdBox: Box<LagerortErstelltEvent> = boxStore.boxFor()
        createdBox.put(
            (0 until 10).map {
                LagerortErstelltEvent(
                    0, Date(), "Nummer $it",
                    "Das ist Lagerort $it, pass gut auf ihn auf!"
                )
            }

        )
    }
}