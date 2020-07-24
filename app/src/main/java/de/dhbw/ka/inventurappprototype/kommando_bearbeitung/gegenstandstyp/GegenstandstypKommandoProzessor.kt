package de.dhbw.ka.inventurappprototype.kommando_bearbeitung.gegenstandstyp

import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp.GegenstandstypBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp.GegenstandstypErstelltEvent
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoProzessor
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.AbstractGegenstandstypKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.GegenstandstypBearbeitenKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstandstyp.GegenstandstypErstellenKommando
import java.util.*

class GegenstandstypKommandoProzessor : KommandoProzessor<AbstractGegenstandstypKommando> {
    override fun bearbeite(kommando: AbstractGegenstandstypKommando): KommandoErgebnis {
        return when (kommando) {
            is GegenstandstypErstellenKommando -> bearbeite(kommando)
            is GegenstandstypBearbeitenKommando -> bearbeite(kommando)
            else -> KommandoErgebnis.NichtAkzeptiert("Unbekanntes Kommando: $kommando")
        }
    }

    private fun bearbeite(kommando: GegenstandstypErstellenKommando): KommandoErgebnis {
        val liste = AktorenKontext.datenbankConnector.gegenstandsTypen.filter {
            it.name == kommando.name
        }

        return when {
            liste.isEmpty() || kommando.erlaubeDoppeltenNamen -> {
                AktorenKontext.eventStream.publish(
                    GegenstandstypErstelltEvent(
                        timestamp = Date(),
                        nutzername = kommando.nutzerName,
                        name = kommando.name,
                        beschreibung = kommando.beschreibung,
                        ID = AktorenKontext.datenbankConnector.freieGegenstandstypId
                    )
                )

                KommandoErgebnis.Akzeptiert
            }
            else -> {
                KommandoErgebnis.NichtAkzeptiert("Gegenstandstyp mit Namen '${kommando.name}' existiert bereits!")
            }
        }
    }


    private fun bearbeite(kommando: GegenstandstypBearbeitenKommando): KommandoErgebnis =
        when(val gegenstandstyp = AktorenKontext.datenbankConnector.gegenstandstyp(kommando.ID)){
            null -> KommandoErgebnis.NichtAkzeptiert("Unbekannter Gegenstandstyp!")
            else -> {
                AktorenKontext.eventStream.publish(
                    GegenstandstypBearbeitetEvent(
                        timestamp = Date(),
                        nutzername = kommando.nutzerName,
                        ID = gegenstandstyp.ID,
                        neuerName = kommando.neuerName,
                        neueBeschreibung = kommando.neueBeschreibung
                    )
                )
                KommandoErgebnis.Akzeptiert
            }
        }
}