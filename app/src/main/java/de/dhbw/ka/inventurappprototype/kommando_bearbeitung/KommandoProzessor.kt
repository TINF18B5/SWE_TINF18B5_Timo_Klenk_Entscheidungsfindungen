package de.dhbw.ka.inventurappprototype.kommando_bearbeitung

import de.dhbw.ka.inventurappprototype.daten.kommandos.AbstractKommando

interface KommandoProzessor<T : AbstractKommando> {
    fun bearbeite(kommando: T): KommandoErgebnis
}