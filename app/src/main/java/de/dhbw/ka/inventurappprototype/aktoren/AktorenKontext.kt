package de.dhbw.ka.inventurappprototype.aktoren

import de.dhbw.ka.inventurappprototype.aktoren.datenbank.DatenbankConnector
import de.dhbw.ka.inventurappprototype.daten.nutzer.Nutzer
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.ZentralerKommandoProzessor

/**
 * Globales Objekt, das eine Aktorenumgebung simulieren soll
 */
object AktorenKontext {
    /**
     * Der EventStream.
     * Alle Events der Anwendung laufen darüber.
     */
    val eventStream: EventStream = EventStream()

    /**
     * Der KommandoProzessor.
     * Alle Kommandos gehen hierher.
     */
    val zentralerKommandoProzessor = ZentralerKommandoProzessor()

    /**
     * Der DatenbankConnector.
     * Wird von [de.dhbw.ka.inventurappprototype.gui.MainActivity] gesetzt.
     */
    lateinit var datenbankConnector: DatenbankConnector

    /**
     * Der eingeloggte Nutzer.
     * Da derzeit noch keine Authentifizierung eingebaut ist, hardcoded.
     */
    val derzeitigerNutzer: Nutzer =
        Nutzer(
            name = "admin",
            passwortHash = "admin",
            sessionToken = "50ME 5E5510N T0KEN"
        )
}