package de.dhbw.ka.inventurappprototype.daten.gegenstandstyp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gegenstandstyp(val name: String, val beschreibung: String, val ID: Int) : Parcelable {
    companion object {
        @JvmStatic
        val none = Gegenstandstyp("", "", 0)
    }
}