package de.dhbw.ka.inventurappprototype.daten.gegenstandstyp

import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer

data class Gegenstandstyp(val name: String, val beschreibung: String)

fun erstelleGegenstandstyp(name: String, beschreibung: String, nutzer: Nutzer): Gegenstandstyp =
    Gegenstandstyp(name, beschreibung) //TODO: Implement