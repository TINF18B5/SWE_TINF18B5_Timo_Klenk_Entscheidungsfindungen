package de.dhbw.ka.inventurappprototype.daten.gegenstandstyp

import android.os.Parcelable
import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gegenstandstyp(val name: String, val beschreibung: String) : Parcelable

fun erstelleGegenstandstyp(name: String, beschreibung: String, nutzer: Nutzer): Gegenstandstyp =
    Gegenstandstyp(name, beschreibung) //TODO: Implement