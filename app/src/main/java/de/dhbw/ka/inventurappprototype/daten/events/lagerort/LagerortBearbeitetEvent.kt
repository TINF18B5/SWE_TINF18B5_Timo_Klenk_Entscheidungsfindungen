package de.dhbw.ka.inventurappprototype.daten.events.lagerort

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import java.util.*

class LagerortBearbeitetEvent(
        timestamp: Date,
        nutzername: String,
        val name: String,
        val neuerName: String,
        val neueBeschreibung: String
) : AbstractEvent(timestamp, nutzername)