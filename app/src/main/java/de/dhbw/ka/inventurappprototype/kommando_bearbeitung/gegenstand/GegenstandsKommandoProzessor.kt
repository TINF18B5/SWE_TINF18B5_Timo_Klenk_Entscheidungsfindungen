package de.dhbw.ka.inventurappprototype.kommando_bearbeitung.gegenstand

import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandErstelltEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandGeloeschtEvent
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstand.*
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoProzessor
import java.util.*

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
        when (val gegenstand =
            AktorenKontext.datenbankConnector.gegenstand(
                kommando.GegenstandstypID,
                kommando.lagerortName
            )) {
            null -> KommandoErgebnis.NichtAkzeptiert("Unbekannter Gegenstand")
            else -> {
                AktorenKontext.eventStream.publish(
                    GegenstandGeloeschtEvent(
                        timestamp = Date(),
                        nutzername = kommando.nutzerName,
                        lagerortName = gegenstand.ort.name,
                        gegenstandstypID = gegenstand.typ.ID,
                        grund = kommando.grund
                    )
                )
                KommandoErgebnis.Akzeptiert
            }
        }

    private fun bearbeite(kommando: GegenstandErstellenKommando): KommandoErgebnis =
        when (AktorenKontext.datenbankConnector.gegenstand(
            kommando.gegenstandstypID,
            kommando.lagerortName
        )) {
            null -> {
                AktorenKontext.eventStream.publish(
                    GegenstandErstelltEvent(
                        timestamp = Date(),
                        nutzername = kommando.nutzerName,
                        menge = kommando.menge,
                        lagerortName = kommando.lagerortName,
                        gegenstandstypID = kommando.gegenstandstypID
                    )
                )
                KommandoErgebnis.Akzeptiert
            }
            else -> KommandoErgebnis.NichtAkzeptiert("Gegenstand existiert bereits!")
        }

    private fun bearbeite(kommando: GegenstandEinlagernKommando): KommandoErgebnis =
        when (val gegenstand =
            AktorenKontext.datenbankConnector.gegenstand(
                kommando.gegenstandstypID,
                kommando.lagerortName
            )) {
            null -> KommandoErgebnis.NichtAkzeptiert("")
            else -> AktorenKontext.zentralerKommandoProzessor.bearbeite(
                GegenstandBearbeitenKommando(
                    nutzerName = kommando.nutzerName,
                    menge = gegenstand.menge + kommando.menge,
                    lagerortName = gegenstand.ort.name,
                    gegenstandstypID = gegenstand.typ.ID
                )
            )
        }

    private fun bearbeite(kommando: GegenstandAuslagernKommando): KommandoErgebnis =
        when (val gegenstand =
            AktorenKontext.datenbankConnector.gegenstand(
                kommando.gegenstandstypID,
                kommando.lagerortName
            )) {
            null -> KommandoErgebnis.NichtAkzeptiert("")
            else -> AktorenKontext.zentralerKommandoProzessor.bearbeite(
                GegenstandBearbeitenKommando(
                    nutzerName = kommando.nutzerName,
                    menge = gegenstand.menge - kommando.menge,
                    lagerortName = gegenstand.ort.name,
                    gegenstandstypID = gegenstand.typ.ID
                )
            )
        }

    private fun bearbeite(kommando: GegenstandBearbeitenKommando): KommandoErgebnis {

        val gegenstand = AktorenKontext.datenbankConnector.gegenstand(
            kommando.gegenstandstypID,
            kommando.lagerortName
        )
        return when {
            gegenstand == null -> KommandoErgebnis.NichtAkzeptiert("Unbekannter Gegenstand!")
            kommando.menge == 0 -> AktorenKontext.zentralerKommandoProzessor.bearbeite(
                GegenstandLoeschenKommando(
                    kommando.nutzerName,
                    kommando.lagerortName,
                    kommando.gegenstandstypID,
                    "Menge auf 0 bearbeitet"
                )
            )
            kommando.menge < 0 -> KommandoErgebnis.NichtAkzeptiert("Menge muss größer als 0 sein!")
            else -> {
                AktorenKontext.eventStream.publish(
                    GegenstandBearbeitetEvent(
                        Date(),
                        kommando.nutzerName,
                        kommando.menge,
                        gegenstand.ort.name,
                        gegenstand.typ.ID
                    )
                )
                KommandoErgebnis.Akzeptiert
            }
        }
    }
}
