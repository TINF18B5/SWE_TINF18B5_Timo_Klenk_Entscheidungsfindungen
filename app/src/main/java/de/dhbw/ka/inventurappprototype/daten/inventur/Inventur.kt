package de.dhbw.ka.inventurappprototype.daten.inventur

import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort

class Inventur(lagerorte: List<Lagerort>) : Iterator<InventurSchritt> {

    private var counter: Int = 0
    private val schritte: List<InventurSchritt> =
        lagerorte.flatMap { AktorenKontext.datenbankConnector.gegenstaende(it) }
            .map { InventurSchritt(it, zaehltZumZweitenMal = false) }

    override fun hasNext(): Boolean = counter < schritte.size

    override fun next(): InventurSchritt = schritte[counter++]

}