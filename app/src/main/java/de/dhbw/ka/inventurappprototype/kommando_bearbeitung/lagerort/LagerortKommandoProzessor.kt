package de.dhbw.ka.inventurappprototype.kommando_bearbeitung.lagerort

import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortErstelltEvent
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.AbstractLagerortKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.LagerortBearbeitenKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.lagerort.LagerortErstellenKommando
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoProzessor
import java.util.*

class LagerortKommandoProzessor : KommandoProzessor<AbstractLagerortKommando> {
    override fun bearbeite(kommando: AbstractLagerortKommando): KommandoErgebnis {
        return when (kommando) {
            is LagerortErstellenKommando -> bearbeite(kommando)
            is LagerortBearbeitenKommando -> bearbeite(kommando)
            else -> KommandoErgebnis.NichtAkzeptiert("Unbekanntes Kommando: $kommando")
        }
    }

    private fun bearbeite(kommando: LagerortErstellenKommando): KommandoErgebnis =
        when (AktorenKontext.datenbankConnector.lagerort(kommando.name)) {
            null -> {
                AktorenKontext.eventStream.publish(
                    LagerortErstelltEvent(
                        timestamp = Date(),
                        nutzername = kommando.nutzerName,
                        name = kommando.name,
                        beschreibung = kommando.beschreibung
                    )
                )
                KommandoErgebnis.Akzeptiert
            }
            else -> KommandoErgebnis.NichtAkzeptiert("Lagerort mit Namen '${kommando.name}' existiert bereits!")
        }

    private fun bearbeite(kommando: LagerortBearbeitenKommando): KommandoErgebnis {
        val lagerort = AktorenKontext.datenbankConnector.lagerort(kommando.name)
        return when {
            lagerort == null -> KommandoErgebnis.NichtAkzeptiert("Unbekannter Lagerort mit Namen '${kommando.name}'")
            lagerort.name == kommando.neuerName -> {
                AktorenKontext.eventStream.publish(
                    LagerortBearbeitetEvent(
                        timestamp = Date(),
                        nutzername = kommando.nutzerName,
                        name = lagerort.name,
                        neuerName = kommando.neuerName,
                        neueBeschreibung = kommando.neueBeschreibung
                    )
                )
                KommandoErgebnis.Akzeptiert
            }
            AktorenKontext.datenbankConnector.lagerort(kommando.neuerName) != null ->
                KommandoErgebnis.NichtAkzeptiert("Kann Lagerort '${lagerort.name}' nicht zu '${kommando.neuerName}' umbenennen, da bereits ein Lagerort mit diesem Namen existiert!")

            else -> {
                AktorenKontext.eventStream.publish(
                    LagerortBearbeitetEvent(
                        timestamp = Date(),
                        nutzername = kommando.nutzerName,
                        name = lagerort.name,
                        neuerName = kommando.neuerName,
                        neueBeschreibung = kommando.neueBeschreibung
                    )
                )
                KommandoErgebnis.Akzeptiert
            }
        }
    }
}