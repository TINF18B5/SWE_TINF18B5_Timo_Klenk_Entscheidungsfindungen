package de.dhbw.ka.inventurappprototype.aktoren

import de.dhbw.ka.inventurappprototype.aktoren.datenbank.DatenbankConnector
import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.ZentralerKommandoProzessor

object AktorenKontext {
    val eventStream: EventStream = EventStream()
    val zentralerKommandoProzessor = ZentralerKommandoProzessor()
    lateinit var datenbankConnector: DatenbankConnector
    val derzeitigerNutzer: Nutzer =
        Nutzer(
            name = "admin",
            passwortHash = "admin",
            sessionToken = "50ME 5E5510N T0KEN"
        )
}