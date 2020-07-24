package de.dhbw.ka.inventurappprototype.daten.events.inventur

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import de.dhbw.ka.inventurappprototype.daten.events.NichtPersistentesEvent
import de.dhbw.ka.inventurappprototype.daten.inventur.InventurSchritt
import java.util.*


class NachsterInventurSchrittEvent(
    timestamp: Date,
    nutzername: String,
    val schritt: InventurSchritt
) : AbstractEvent(timestamp, nutzername), NichtPersistentesEvent