package de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import java.util.*

class GegenstandstypBearbeitetEvent(
        timestamp: Date,
        nutzername: String,
        val ID: Int,
        val neuerName: String,
        val neueBeschreibung: String
) : AbstractEvent(timestamp, nutzername)