package de.dhbw.ka.inventurappprototype.daten.inventur

import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand

data class InventurSchritt(val zuPruefen: Gegenstand, val zaehltZumZweitenMal: Boolean) {
    fun gezaehltMenge(menge: Int): GezaehltStatus {
        TODO("implement")
    }
}
