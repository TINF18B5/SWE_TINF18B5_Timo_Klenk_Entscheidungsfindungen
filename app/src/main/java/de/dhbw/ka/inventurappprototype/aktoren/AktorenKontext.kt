package de.dhbw.ka.inventurappprototype.aktoren

import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.ZentralerKommandoProzessor

object AktorenKontext {
    val eventStream: EventStream = EventStream()
    val zentralerKommandoProzessor = ZentralerKommandoProzessor()
    val datenbankConnector = DatenbankConnector()
}