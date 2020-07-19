package de.dhbw.ka.inventurappprototype.daten.gegenstand

import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort
import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer

data class Gegenstand(val typ: Gegenstandstyp, val ort: Lagerort, var menge: Int)

fun erstelleGegenstand(typ: Gegenstandstyp, ort: Lagerort, menge: Int, nutzer: Nutzer): Gegenstand =
    Gegenstand(typ, ort, menge) //TODO: Implement