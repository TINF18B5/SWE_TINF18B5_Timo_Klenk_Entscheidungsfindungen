package de.dhbw.ka.inventurappprototype.daten.events.inventur

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import java.util.*

class InventurBeendetEvent(timestamp: Date, nutzername: String) :
    AbstractEvent(timestamp, nutzername)