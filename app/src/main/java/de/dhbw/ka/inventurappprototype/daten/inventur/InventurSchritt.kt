package de.dhbw.ka.inventurappprototype.daten.inventur

import android.os.Parcelable
import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InventurSchritt(val zuPruefen: Gegenstand, val zaehltZumZweitenMal: Boolean) :
    Parcelable
