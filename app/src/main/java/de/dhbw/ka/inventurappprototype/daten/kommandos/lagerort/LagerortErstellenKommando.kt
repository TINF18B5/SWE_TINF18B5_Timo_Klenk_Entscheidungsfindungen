package de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort

class LagerortErstellenKommando(
    nutzerName: String,
    val name: String,
    val beschreibung: String
) : AbstractLagerortKommando(nutzerName)