package de.dhbw.ka.inventurappprototype.daten.gegenstand

import android.os.Parcelable
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort
import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gegenstand(val typ: Gegenstandstyp, val ort: Lagerort, var menge: Int) : Parcelable

fun erstelleGegenstand(typ: Gegenstandstyp, ort: Lagerort, menge: Int, nutzer: Nutzer): Gegenstand =
    Gegenstand(typ, ort, menge) //TODO: Implement