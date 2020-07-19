package de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstand

class GegenstandLoeschenKommando(
    nutzerName: String,
    val lagerortName: String,
    val GegenstandstypID: Int
) : AbstractGegenstandKommando(nutzerName)