package de.dhbw.ka.inventurappprototype.kommando_bearbeitung.gegenstandstyp

import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoProzessor
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.AbstractGegenstandstypKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.GegenstandstypBearbeitenKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.GegenstandstypErstellenKommando

class GegenstandstypKommandoProzessor : KommandoProzessor<AbstractGegenstandstypKommando> {
    override fun bearbeite(kommando: AbstractGegenstandstypKommando): KommandoErgebnis {
        return when (kommando) {
            is GegenstandstypErstellenKommando -> bearbeite(kommando)
            is GegenstandstypBearbeitenKommando -> bearbeite(kommando)
            else -> KommandoErgebnis.NichtAkzeptiert("Unbekanntes Kommando: $kommando")
        }
    }

    private fun bearbeite(kommando: GegenstandstypErstellenKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert

    private fun bearbeite(kommando: GegenstandstypBearbeitenKommando): KommandoErgebnis =
        KommandoErgebnis.Akzeptiert
}