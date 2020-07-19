package de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort

class LagerortBearbeitenKommando(
    nutzerName: String,
    val name: String,
    val neuerName: String,
    val neueBeschreibung: String
) : AbstractLagerortKommando(nutzerName)