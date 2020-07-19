package de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstand

class GegenstandEinlagernKommando(
    nutzerName: String,
    val menge: Int,
    val lagerortName: String,
    val gegenstandstypID: Int
) : AbstractGegenstandKommando(nutzerName)
