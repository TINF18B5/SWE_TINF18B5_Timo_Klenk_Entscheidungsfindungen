package de.dhbw.ka.inventurappprototype.kommando_bearbeitung

sealed class KommandoErgebnis {
    object Akzeptiert : KommandoErgebnis()
    data class NichtAkzeptiert(val fehler: String): KommandoErgebnis()
}