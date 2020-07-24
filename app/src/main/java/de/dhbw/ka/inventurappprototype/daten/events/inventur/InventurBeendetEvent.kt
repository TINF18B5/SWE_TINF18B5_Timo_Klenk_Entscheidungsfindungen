package de.dhbw.ka.inventurappprototype.daten.events.inventur

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import de.dhbw.ka.inventurappprototype.daten.events.NichtPersistentesEvent
import java.util.*

class InventurBeendetEvent(timestamp: Date, nutzername: String) :
    AbstractEvent(timestamp, nutzername), NichtPersistentesEvent