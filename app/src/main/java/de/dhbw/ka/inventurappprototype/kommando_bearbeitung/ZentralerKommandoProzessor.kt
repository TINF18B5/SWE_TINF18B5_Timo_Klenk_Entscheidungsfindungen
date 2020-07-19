package de.dhbw.ka.inventurappprototype.kommando_bearbeitung

import de.dhbw.ka.inventurappprototype.daten.kommandos.AbstractKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstand.AbstractGegenstandKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.AbstractGegenstandstypKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.AbstractLagerortKommando
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.gegenstand.GegenstandsKommandoProzessor
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.gegenstandstyp.GegenstandstypKommandoProzessor
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.lagerort.LagerortKommandoProzessor

class ZentralerKommandoProzessor {
    private val gegenstandsKommandoProzessor: GegenstandsKommandoProzessor =
        GegenstandsKommandoProzessor()
    private val gegenstandstypKommandoProzessor: GegenstandstypKommandoProzessor =
        GegenstandstypKommandoProzessor()
    private val lagerortKommandoProzessor: LagerortKommandoProzessor = LagerortKommandoProzessor()


    @Suppress("MemberVisibilityCanBePrivate")
    fun bearbeite(kommando: AbstractKommando): ZentralerKommandoProzessor {
        val result: KommandoErgebnis = when (kommando) {
            is AbstractGegenstandKommando -> gegenstandsKommandoProzessor.bearbeite(kommando)
            is AbstractGegenstandstypKommando -> gegenstandstypKommandoProzessor.bearbeite(kommando)
            is AbstractLagerortKommando -> lagerortKommandoProzessor.bearbeite(kommando)
            else -> KommandoErgebnis.NichtAkzeptiert("Unbekanntes Kommando $kommando")
        }

        //if(result is KommandoErgebnis.NichtAkzeptiert) {
        //    TODO("Wirf fehler für ${result.fehler}")
        //}

        return this
    }
}