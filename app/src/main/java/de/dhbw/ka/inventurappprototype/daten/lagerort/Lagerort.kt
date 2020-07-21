package de.dhbw.ka.inventurappprototype.daten.lagerort

import android.os.Parcelable
import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lagerort(val name: String, val beschreibung: String) : Parcelable

fun erstelleLagerort(name: String, beschreibung: String, nutzer: Nutzer): Lagerort
    = Lagerort(name, beschreibung) //TODO: Implement