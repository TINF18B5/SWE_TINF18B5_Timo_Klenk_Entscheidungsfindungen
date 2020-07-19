package de.dhbw.ka.inventurappprototype.aktoren

import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp

class DatenbankConnector {
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