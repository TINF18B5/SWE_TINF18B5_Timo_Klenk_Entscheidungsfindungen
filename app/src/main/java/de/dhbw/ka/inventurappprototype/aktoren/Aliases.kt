package de.dhbw.ka.inventurappprototype.aktoren

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent

typealias EventListener<T> = (event: T) -> Unit