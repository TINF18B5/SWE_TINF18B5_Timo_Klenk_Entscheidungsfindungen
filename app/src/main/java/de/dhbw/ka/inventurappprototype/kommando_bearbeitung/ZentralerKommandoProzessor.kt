package de.dhbw.ka.inventurappprototype.kommando_bearbeitung

import de.dhbw.ka.inventurappprototype.daten.kommandos.AbstractKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstand.AbstractGegenstandKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.AbstractGegenstandstypKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.inventur.AbstractInventurKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.AbstractLagerortKommando
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.gegenstand.GegenstandsKommandoProzessor
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.gegenstandstyp.GegenstandstypKommandoProzessor
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.inventur.InventurKommandoProzessor
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.lagerort.LagerortKommandoProzessor

class ZentralerKommandoProzessor {
    private val gegenstandsKommandoProzessor: GegenstandsKommandoProzessor =
        GegenstandsKommandoProzessor()
    private val gegenstandstypKommandoProzessor: GegenstandstypKommandoProzessor =
        GegenstandstypKommandoProzessor()
    private val lagerortKommandoProzessor: LagerortKommandoProzessor =
        LagerortKommandoProzessor()
    private val inventurKommandoProzessor: InventurKommandoProzessor =
        InventurKommandoProzessor()


    @Suppress("MemberVisibilityCanBePrivate")
    fun bearbeite(kommando: AbstractKommando): KommandoErgebnis {
        return when (kommando) {
            is AbstractGegenstandKommando -> gegenstandsKommandoProzessor.bearbeite(kommando)
            is AbstractGegenstandstypKommando -> gegenstandstypKommandoProzessor.bearbeite(kommando)
            is AbstractLagerortKommando -> lagerortKommandoProzessor.bearbeite(kommando)
            is AbstractInventurKommando -> inventurKommandoProzessor.bearbeite(kommando)
            else -> KommandoErgebnis.NichtAkzeptiert("Unbekanntes Kommando $kommando")
        }
    }
}