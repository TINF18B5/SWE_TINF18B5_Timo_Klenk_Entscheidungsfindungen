package de.dhbw.ka.inventurappprototype.daten.gegenstandstyp

import android.os.Parcelable
import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Gegenstandstyp(val name: String, val beschreibung: String, val ID: Int) : Parcelable

fun erstelleGegenstandstyp(name: String, beschreibung: String, nutzer: Nutzer): Gegenstandstyp =
    Gegenstandstyp(name, beschreibung, Random().nextInt(Int.MAX_VALUE)) //TODO: Implement