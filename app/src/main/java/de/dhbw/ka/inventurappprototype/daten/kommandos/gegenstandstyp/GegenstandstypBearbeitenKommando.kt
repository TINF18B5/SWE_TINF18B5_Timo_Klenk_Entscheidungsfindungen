package de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp

class GegenstandstypBearbeitenKommando(
    nutzerName: String,
    val ID: Int,
    val neuerName: String,
    val neueBeschreibung: String
) : AbstractGegenstandstypKommando(nutzerName)