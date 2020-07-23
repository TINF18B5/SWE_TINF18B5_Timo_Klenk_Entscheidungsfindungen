package de.dhbw.ka.inventurappprototype.daten.kommandos.inventur

import de.dhbw.ka.inventurappprototype.daten.inventur.Inventur

class InventurStartenKommando(nutzerName: String, val inventur: Inventur) :
    AbstractInventurKommando(nutzerName)