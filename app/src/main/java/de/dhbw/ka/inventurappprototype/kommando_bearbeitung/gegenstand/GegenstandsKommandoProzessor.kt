package de.dhbw.ka.inventurappprototype.kommando_bearbeitung.gegenstand

import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoProzessor
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstand.*

class GegenstandsKommandoProzessor : KommandoProzessor<AbstractGegenstandKommando> {
    override fun bearbeite(kommando: AbstractGegenstandKommando): KommandoErgebnis {
        return when (kommando) {
            is GegenstandLoeschenKommando -> bearbeite(kommando)
            is GegenstandErstellenKommando -> bearbeite(kommando)
            is GegenstandEinlagernKommando -> bearbeite(kommando)
            is GegenstandAuslagernKommando -> bearbeite(kommando)
            is GegenstandBearbeitenKommando -> bearbeite(kommando)
            else -> KommandoErgebnis.NichtAkzeptiert("Unbekanntes Kommando: $kommando")
        }
    }

    private fun bearbeite(kommando: GegenstandLoeschenKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert

    private fun bearbeite(kommando: GegenstandErstellenKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert

    private fun bearbeite(kommando: GegenstandEinlagernKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert

    private fun bearbeite(kommando: GegenstandAuslagernKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert

    private fun bearbeite(kommando: GegenstandBearbeitenKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert
}