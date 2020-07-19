package de.dhbw.ka.inventurappprototype.kommando_bearbeitung.lagerort

import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoProzessor
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.AbstractLagerortKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.LagerortBearbeitenKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.LagerortErstellenKommando

class LagerortKommandoProzessor : KommandoProzessor<AbstractLagerortKommando> {
    override fun bearbeite(kommando: AbstractLagerortKommando): KommandoErgebnis {
        return when (kommando) {
            is LagerortErstellenKommando -> bearbeite(kommando)
            is LagerortBearbeitenKommando -> bearbeite(kommando)
            else -> KommandoErgebnis.NichtAkzeptiert("Unbekanntes Kommando: $kommando")
        }
    }

    private fun bearbeite(kommando: LagerortErstellenKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert

    private fun bearbeite(kommando: LagerortBearbeitenKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert
}