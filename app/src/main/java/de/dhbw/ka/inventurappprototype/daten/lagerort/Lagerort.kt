package de.dhbw.ka.inventurappprototype.daten.lagerort

import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer

data class Lagerort(val name: String, val beschreibung: String)

fun erstelleLagerort(name: String, beschreibung: String, nutzer: Nutzer): Lagerort
    = Lagerort(name, beschreibung) //TODO: Implement