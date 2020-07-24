package de.dhbw.ka.inventurappprototype.aktoren

/**
 * Gibt die Priorität an, mit der auf ein Event subskribiert wird.
 * Alle [HIGH] handler werden immer vor den [NORMAL] handlern für ein spezifisches Event ausgeführt.
 */
enum class EventPriority {
    HIGH, NORMAL
}