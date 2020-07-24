package de.dhbw.ka.inventurappprototype.daten.lagerort

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lagerort(val name: String, val beschreibung: String) : Parcelable