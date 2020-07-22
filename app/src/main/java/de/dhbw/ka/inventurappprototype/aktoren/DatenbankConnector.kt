package de.dhbw.ka.inventurappprototype.aktoren

import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort
import de.dhbw.ka.inventurappprototype.gui.gegenstand.liste.GegenstandsListenArt

class DatenbankConnector {
    fun gegenstaende(listenart: GegenstandsListenArt): List<Gegenstand> {
        return when (listenart) {
            is GegenstandsListenArt.LagerortVerwaltung -> gegenstaende(listenart.lagerort)
            is GegenstandsListenArt.GegenstandstypVerwaltung -> gegenstaende(listenart.gegenstandstyp)
        }
    }

    fun gegenstaende(lagerort: Lagerort): List<Gegenstand> = listOf()

    fun gegenstaende(gegenstandstyp: Gegenstandstyp): List<Gegenstand> = listOf(
        Gegenstand(gegenstandstyp, Lagerort("Lager 1", "Beschreibung 1"), 100),
        Gegenstand(gegenstandstyp, Lagerort("Lager 2", "Beschreibung 2"), 1),
        Gegenstand(gegenstandstyp, Lagerort("Lager 3", "Beschreibung 3"), 20),
        Gegenstand(gegenstandstyp, Lagerort("Lager 4", "Beschreibung 4"), 30)
    )

    val lagerorte: List<Lagerort>
        get() = listOf(
            Lagerort("Name1", "Beschreibung 1"),
            Lagerort("Name2", "Beschreibung 2"),
            Lagerort("Name3", "Beschreibung 3"),
            Lagerort("Name4", "Beschreibung 4"),
            Lagerort("Name5", "Beschreibung 5"),
            Lagerort("Name6", "Beschreibung 6")
        )
    val gegenstandsTypen: List<Gegenstandstyp>
        get() = listOf(
            Gegenstandstyp("Name1", "Beschreibung 1"),
            Gegenstandstyp("Name2", "Beschreibung 2"),
            Gegenstandstyp("Name3", "Beschreibung 3"),
            Gegenstandstyp("Name4", "Beschreibung 4"),
            Gegenstandstyp("Name5", "Beschreibung 5"),
            Gegenstandstyp("Name6", "Beschreibung 6")
        )
}