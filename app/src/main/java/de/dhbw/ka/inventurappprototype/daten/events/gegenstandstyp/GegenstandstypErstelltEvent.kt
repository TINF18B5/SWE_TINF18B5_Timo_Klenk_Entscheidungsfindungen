package de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import java.util.*

class GegenstandstypErstelltEvent(
        timestamp: Date,
        nutzername: String,
        val name: String,
        val beschreibung: String,
        val ID: Int
) : AbstractEvent(timestamp, nutzername)