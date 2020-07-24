package de.dhbw.ka.inventurappprototype.daten.inventur

import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort

/**
 * Eine Inventur besteht aus vielen Inventurschritten.
 * Wird daher als Iterator implementiert.
 */
class Inventur(lagerorte: List<Lagerort>) : Iterator<InventurSchritt> {

    private var counter: Int = 0
    private val schritte: List<InventurSchritt> =
        lagerorte.flatMap { AktorenKontext.datenbankConnector.gegenstaende(it) }
            .map { InventurSchritt(it, zaehltZumZweitenMal = false) }

    /**
     * Solange es weitere Schritte gibt, hibt das hier true zuruck.
     * True impliziert, dass [next] einen Wert liefert.
     */
    override fun hasNext(): Boolean = counter < schritte.size

    /**
     * Gibt den nächsten Inventurschritt.
     * Nur definiert, solange [hasNext] true zurückgibt.
     * Verändert den Zustand dieses Objekts.
     *
     * @throws IndexOutOfBoundsException Wenn [hasNext] false gibt, wird hier eine Boundary überschritten!
     */
    override fun next(): InventurSchritt = schritte[counter++]

}