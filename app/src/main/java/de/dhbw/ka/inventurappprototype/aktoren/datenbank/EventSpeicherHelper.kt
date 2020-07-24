package de.dhbw.ka.inventurappprototype.aktoren.datenbank

import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import de.dhbw.ka.inventurappprototype.daten.events.NichtPersistentesEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.*
import de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp.GegenstandstypBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp.GegenstandstypErstelltEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortErstelltEvent

class EventSpeicherHelper {
    companion object {
        /**
         * Speichert das entsprechende Event in der [writableDatabase].
         * Events vom Typ [NichtPersistentesEvent] werden ignoriert.
         * Andere Events werden an entsprechende funktionen (s. unten) delegiert.
         * 'Vergessene' Events werfen einen error.
         */
        fun speicherEvent(event: AbstractEvent, writableDatabase: SQLiteDatabase) {
            when (event) {
                is NichtPersistentesEvent -> return
                is GegenstandAusgelagertEvent -> speicherEvent(event, writableDatabase)
                is GegenstandBearbeitetEvent -> speicherEvent(event, writableDatabase)
                is GegenstandEingelagertEvent -> speicherEvent(event, writableDatabase)
                is GegenstandErstelltEvent -> speicherEvent(event, writableDatabase)
                is GegenstandGeloeschtEvent -> speicherEvent(event, writableDatabase)
                is GegenstandstypBearbeitetEvent -> speicherEvent(event, writableDatabase)
                is GegenstandstypErstelltEvent -> speicherEvent(event, writableDatabase)
                is LagerortBearbeitetEvent -> speicherEvent(event, writableDatabase)
                is LagerortErstelltEvent -> speicherEvent(event, writableDatabase)
                else -> error("Unbekanntes Event: $event")
            }
        }


        private fun speicherEvent(
            event: GegenstandAusgelagertEvent,
            writableDatabase: SQLiteDatabase
        ) {
            writableDatabase.insert(
                InventurAppDatabaseContract.GegenstandAusgelagertEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandAusgelagertEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.GegenstandAusgelagertEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.GegenstandAusgelagertEventEntry.COLUMN_GEGENSTANDSTYP_ID to event.GegenstandstypID,
                    InventurAppDatabaseContract.GegenstandAusgelagertEventEntry.COLUMN_LAGERORT_NAME to event.lagerortName,
                    InventurAppDatabaseContract.GegenstandAusgelagertEventEntry.COLUMN_MENGE to event.menge
                )
            )
        }

        private fun speicherEvent(
            event: GegenstandBearbeitetEvent,
            writableDatabase: SQLiteDatabase
        ) {
            writableDatabase.insert(
                InventurAppDatabaseContract.GegenstandBearbeitetEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandBearbeitetEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.GegenstandBearbeitetEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.GegenstandBearbeitetEventEntry.COLUMN_GEGENSTANDSTYP_ID to event.gegenstandstypID,
                    InventurAppDatabaseContract.GegenstandBearbeitetEventEntry.COLUMN_LAGERORT_NAME to event.lagerortName,
                    InventurAppDatabaseContract.GegenstandBearbeitetEventEntry.COLUMN_NEUE_MENGE to event.menge
                )
            )
        }

        private fun speicherEvent(
            event: GegenstandEingelagertEvent,
            writableDatabase: SQLiteDatabase
        ) {
            writableDatabase.insert(
                InventurAppDatabaseContract.GegenstandEingelagertEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandEingelagertEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.GegenstandEingelagertEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.GegenstandEingelagertEventEntry.COLUMN_GEGENSTANDSTYP_ID to event.GegenstandstypID,
                    InventurAppDatabaseContract.GegenstandEingelagertEventEntry.COLUMN_LAGERORT_NAME to event.lagerortName,
                    InventurAppDatabaseContract.GegenstandEingelagertEventEntry.COLUMN_MENGE to event.menge
                )
            )
        }

        private fun speicherEvent(
            event: GegenstandErstelltEvent,
            writableDatabase: SQLiteDatabase
        ) {
            writableDatabase.insert(
                InventurAppDatabaseContract.GegenstandErstelltEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandErstelltEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.GegenstandErstelltEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.GegenstandErstelltEventEntry.COLUMN_GEGENSTANDSTYP_ID to event.gegenstandstypID,
                    InventurAppDatabaseContract.GegenstandErstelltEventEntry.COLUMN_LAGERORT_NAME to event.lagerortName,
                    InventurAppDatabaseContract.GegenstandErstelltEventEntry.COLUMN_MENGE to event.menge
                )
            )
        }

        private fun speicherEvent(
            event: GegenstandGeloeschtEvent,
            writableDatabase: SQLiteDatabase
        ) {
            writableDatabase.insert(
                InventurAppDatabaseContract.GegenstandGeloschtEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandGeloschtEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.GegenstandGeloschtEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.GegenstandGeloschtEventEntry.COLUMN_GEGENSTANDSTYP_ID to event.gegenstandstypID,
                    InventurAppDatabaseContract.GegenstandGeloschtEventEntry.COLUMN_LAGERORT_NAME to event.lagerortName,
                    InventurAppDatabaseContract.GegenstandGeloschtEventEntry.COLUMN_GRUND to event.grund
                )
            )
        }

        private fun speicherEvent(
            event: GegenstandstypBearbeitetEvent,
            writableDatabase: SQLiteDatabase
        ) {
            writableDatabase.insert(
                InventurAppDatabaseContract.GegenstandstypBearbeitetEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandstypBearbeitetEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.GegenstandstypBearbeitetEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.GegenstandstypBearbeitetEventEntry.COLUMN_GEGENSTANDSTYP_ID to event.ID,
                    InventurAppDatabaseContract.GegenstandstypBearbeitetEventEntry.COLUMN_NEUE_BESCHREIBUNG to event.neueBeschreibung,
                    InventurAppDatabaseContract.GegenstandstypBearbeitetEventEntry.COLUMN_NEUER_NAME to event.neuerName
                )
            )
        }

        private fun speicherEvent(
            event: GegenstandstypErstelltEvent,
            writableDatabase: SQLiteDatabase
        ) {
            writableDatabase.insert(
                InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_GEGENSTANDSTYP_ID to event.ID,
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_NAME to event.name,
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_BESCHREIBUNG to event.beschreibung
                )
            )

        }

        private fun speicherEvent(
            event: LagerortBearbeitetEvent,
            writableDatabase: SQLiteDatabase
        ) {
            writableDatabase.insert(
                InventurAppDatabaseContract.LagerortBearbeitetEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.LagerortBearbeitetEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.LagerortBearbeitetEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.LagerortBearbeitetEventEntry.COLUMN_NAME to event.name,
                    InventurAppDatabaseContract.LagerortBearbeitetEventEntry.COLUMN_NEUE_BESCHREIBUNG to event.neueBeschreibung,
                    InventurAppDatabaseContract.LagerortBearbeitetEventEntry.COLUMN_NEUER_NAME to event.neuerName
                )
            )
        }

        private fun speicherEvent(event: LagerortErstelltEvent, writableDatabase: SQLiteDatabase) {
            writableDatabase.insert(
                InventurAppDatabaseContract.LagerortErstelltEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.LagerortErstelltEventEntry.COLUMN_ZEITSTEMPEL to event.datenBankZeitstempel,
                    InventurAppDatabaseContract.LagerortErstelltEventEntry.COLUMN_NUTZER to event.nutzername,
                    InventurAppDatabaseContract.LagerortErstelltEventEntry.COLUMN_NAME to event.name,
                    InventurAppDatabaseContract.LagerortErstelltEventEntry.COLUMN_BESCHREIBUNG to event.beschreibung
                )
            )
        }
    }
}

val AbstractEvent.datenBankZeitstempel get() = this.timestamp.time