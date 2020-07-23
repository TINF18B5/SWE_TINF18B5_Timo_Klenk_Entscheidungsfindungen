package de.dhbw.ka.inventurappprototype.daten.events.gegenstand

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import java.util.*

class GegenstandGeloeschtEvent(
        timestamp: Date,
        nutzername: String,
        val lagerortName: String,
        val gegenstandstypID: Int,
        val grund: String
) : AbstractEvent(timestamp, nutzername) {
}