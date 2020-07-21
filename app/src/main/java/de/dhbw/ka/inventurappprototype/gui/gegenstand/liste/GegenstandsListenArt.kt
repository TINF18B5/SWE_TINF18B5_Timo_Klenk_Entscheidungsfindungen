package de.dhbw.ka.inventurappprototype.gui.gegenstand.liste

import android.os.Parcelable
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort
import kotlinx.android.parcel.Parcelize

sealed class GegenstandsListenArt : Parcelable {
    @Parcelize
    data class LagerortVerwaltung(val lagerort: Lagerort) : GegenstandsListenArt()

    @Parcelize
    data class GegenstandstypVerwaltung(val gegenstandstyp: Gegenstandstyp) : GegenstandsListenArt()
}