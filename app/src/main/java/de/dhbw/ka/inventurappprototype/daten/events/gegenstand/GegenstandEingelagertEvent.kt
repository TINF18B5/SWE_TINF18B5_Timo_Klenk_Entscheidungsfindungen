package de.dhbw.ka.inventurappprototype.daten.events.gegenstand

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import java.util.*

class GegenstandEingelagertEvent(
        timestamp: Date,
        nutzername: String,
        val menge: Int,
        val lagerortName: String,
        val GegenstandstypID: Int
) : AbstractEvent(timestamp, nutzername)