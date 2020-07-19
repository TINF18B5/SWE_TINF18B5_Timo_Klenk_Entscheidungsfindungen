package de.dhbw.ka.inventurappprototype.daten.events.lagerort

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import java.util.*

class LagerortErstelltEvent(
        timestamp: Date,
        nutzername: String,
        val name: String,
        val beschreibung: String
) : AbstractEvent(timestamp, nutzername)