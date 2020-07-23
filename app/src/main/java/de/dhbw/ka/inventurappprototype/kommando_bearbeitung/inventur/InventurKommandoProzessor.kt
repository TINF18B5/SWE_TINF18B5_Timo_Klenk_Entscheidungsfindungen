package de.dhbw.ka.inventurappprototype.kommando_bearbeitung.inventur

import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandGeloeschtEvent
import de.dhbw.ka.inventurappprototype.daten.events.inventur.InventurBeendetEvent
import de.dhbw.ka.inventurappprototype.daten.events.inventur.NachsterInventurSchrittEvent
import de.dhbw.ka.inventurappprototype.daten.inventur.Inventur
import de.dhbw.ka.inventurappprototype.daten.inventur.InventurSchritt
import de.dhbw.ka.inventurappprototype.daten.kommandos.inventur.AbstractInventurKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.inventur.InventurAbbrechenKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.inventur.InventurSchrittValidierenKommando
import de.dhbw.ka.inventurappprototype.daten.kommandos.inventur.InventurStartenKommando
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoErgebnis
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoProzessor
import java.util.*

class InventurKommandoProzessor : KommandoProzessor<AbstractInventurKommando> {
    private var inventur: Inventur? = null
    private var nutzerName: String? = null
    private lateinit var schritt: InventurSchritt

    override fun bearbeite(kommando: AbstractInventurKommando): KommandoErgebnis {
        return when (kommando) {
            is InventurStartenKommando -> bearbeite(kommando)
            is InventurSchrittValidierenKommando -> bearbeite(kommando)
            is InventurAbbrechenKommando -> bearbeite(kommando)
            else -> KommandoErgebnis.NichtAkzeptiert("Unbekanntes Kommando: $kommando")
        }
    }

    private fun bearbeite(kommando: InventurStartenKommando): KommandoErgebnis =
        when (inventur) {
            null -> {
                nutzerName = kommando.nutzerName
                inventur = kommando.inventur
                findeNachstenSchritt()
                KommandoErgebnis.Akzeptiert
            }
            else -> KommandoErgebnis.NichtAkzeptiert("Es ist bereits eine Inventur im Gange")
        }
    @Suppress("UNUSED_PARAMETER")
    private fun bearbeite(kommando: InventurAbbrechenKommando): KommandoErgebnis =
        when(inventur) {
            null -> KommandoErgebnis.NichtAkzeptiert("Keine Inventur, die abgebrochen werden kann")
            else -> {
                inventur = null
                AktorenKontext.eventStream.publish(InventurBeendetEvent(Date(), nutzerName!!))
                nutzerName = null
                KommandoErgebnis.Akzeptiert
            }
        }

    private fun bearbeite(kommando: InventurSchrittValidierenKommando): KommandoErgebnis =
        when {
            kommando.menge == schritt.zuPruefen.menge -> {
                findeNachstenSchritt()
                KommandoErgebnis.Akzeptiert
            }
            !schritt.zaehltZumZweitenMal -> {
                schritt = schritt.copy(zaehltZumZweitenMal = true)
                AktorenKontext.eventStream.publish(
                    NachsterInventurSchrittEvent(
                        Date(),
                        nutzerName!!,
                        schritt
                    )
                )
                KommandoErgebnis.NichtAkzeptiert("Menge weicht von erwarteter Menge ab, bitte noch einmal zählen")
            }
            kommando.menge > 0 -> {
                AktorenKontext.eventStream.publish(
                    GegenstandBearbeitetEvent(
                        Date(),
                        nutzerName!!,
                        kommando.menge,
                        schritt.zuPruefen.ort.name,
                        schritt.zuPruefen.typ.ID
                    )
                )
                findeNachstenSchritt()
                KommandoErgebnis.Akzeptiert
            }
            kommando.menge == 0 -> {
                AktorenKontext.eventStream.publish(
                    GegenstandGeloeschtEvent(
                        Date(),
                        nutzerName!!,
                        schritt.zuPruefen.ort.name,
                        schritt.zuPruefen.typ.ID,
                        "Wurde in einer Inventur auf 0 gezählt"
                    )
                )
                findeNachstenSchritt()
                KommandoErgebnis.Akzeptiert
            }
            else -> KommandoErgebnis.NichtAkzeptiert("Menge ungültig: ${kommando.menge}")
        }

    private fun findeNachstenSchritt() {
        val tempInv = inventur
        val tempNutzer = nutzerName
        if (tempInv == null || tempNutzer == null)
            return

        AktorenKontext.eventStream.publish(
            if (tempInv.hasNext()) {
                schritt = tempInv.next()
                NachsterInventurSchrittEvent(
                    Date(),
                    tempNutzer,
                    schritt = schritt
                )
            } else {
                InventurBeendetEvent(
                    Date(),
                    tempNutzer
                )
            }
        )
    }
}