package de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp

class GegenstandstypErstellenKommando(
    nutzerName: String,
    val name: String,
    val beschreibung: String,
    val erlaubeDoppeltenNamen: Boolean
) : AbstractGegenstandstypKommando(nutzerName)