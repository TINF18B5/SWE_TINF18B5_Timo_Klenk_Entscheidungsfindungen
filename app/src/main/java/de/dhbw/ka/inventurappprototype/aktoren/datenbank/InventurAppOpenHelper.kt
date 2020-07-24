package de.dhbw.ka.inventurappprototype.aktoren.datenbank

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import java.util.*
import kotlin.random.Random

const val filename = "InventurApp.db"
const val version = 1

/**
 * Adapter, der sich um den Aufbau der Verbindung zu SQLite Datenbank kümmert.
 * Sollte am Ende der Anwendung geschlossen werden. (s. [close]).
 * Wird im [DatenbankConnector] benutzt
 */
class InventurAppOpenHelper(
    context: Context?
) : SQLiteOpenHelper(context, filename, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null) {
            error("Null Database?!?")
        }

        db.execSQL(InventurAppDatabaseContract.LagerortEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandstypEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.NutzerEntry.CREATE_SQL)

        db.execSQL(InventurAppDatabaseContract.LagerortBearbeitetEventEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.LagerortErstelltEventEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandGeloschtEventEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandEingelagertEventEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandAusgelagertEventEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandBearbeitetEventEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandErstelltEventEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.CREATE_SQL)
        db.execSQL(InventurAppDatabaseContract.GegenstandstypBearbeitetEventEntry.CREATE_SQL)

        standardWerteEinfugen(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Wir sind auf Datenbank Version 1 also keine Upgrades nötig.
    }

    /**
     * Erstellt einen Nutzer.
     * Erstellt je 5 Lagerorte und Gegenstandstypen.
     * Erzeugt zufällige Gegenstände auf deren Basis.
     * Die Gegenstände sind fest ge-seeded, also sehen zwei neu erzeugte Datenbanken gleich aus.
     */
    private fun standardWerteEinfugen(db: SQLiteDatabase) {
        val timestampCalendar: Calendar = GregorianCalendar.getInstance()
        timestampCalendar.set(2020, 7, 10, 12, 12, 0)
        val zeitstempel = timestampCalendar.time.toInstant().toEpochMilli()
        val nutzer = "admin"

        db.insert(
            InventurAppDatabaseContract.NutzerEntry.TABLE_NAME,
            null,
            contentValuesOf(
                InventurAppDatabaseContract.NutzerEntry.COLUMN_NAME to nutzer,
                InventurAppDatabaseContract.NutzerEntry.COLUMN_PASSWORD_HASH to "admin",
                InventurAppDatabaseContract.NutzerEntry.COLUMN_SESSION_TOKEN to "50ME 5E5510N T0KEN"
            )
        )

        for (i in 0..5) {
            val lagerortName = "Lagerort L$i"
            val lagerortBeschreibung = "Beschreibung L$i"

            db.insert(
                InventurAppDatabaseContract.LagerortEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.LagerortEntry.COLUMN_NAME to lagerortName,
                    InventurAppDatabaseContract.LagerortEntry.COLUMN_BESCHREIBUNG to lagerortBeschreibung
                )
            )


            db.insert(
                InventurAppDatabaseContract.LagerortErstelltEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.LagerortErstelltEventEntry.COLUMN_ZEITSTEMPEL to zeitstempel,
                    InventurAppDatabaseContract.LagerortErstelltEventEntry.COLUMN_NUTZER to nutzer,
                    InventurAppDatabaseContract.LagerortErstelltEventEntry.COLUMN_NAME to lagerortName,
                    InventurAppDatabaseContract.LagerortErstelltEventEntry.COLUMN_BESCHREIBUNG to lagerortBeschreibung
                )
            )
        }

        for (gegenstandstypID in 0..5) {
            val gegenstandstypName = "Gegenstandstyp G$gegenstandstypID"
            val gegenstandstypBeschreibung = "Beschreibung G$gegenstandstypID"
            db.insert(
                InventurAppDatabaseContract.GegenstandstypEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_ID to gegenstandstypID,
                    InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_NAME to gegenstandstypName,
                    InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_BESCHREIBUNG to gegenstandstypBeschreibung
                )
            )

            db.insert(
                InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_ZEITSTEMPEL to zeitstempel,
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_NUTZER to nutzer,
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_NAME to gegenstandstypName,
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_BESCHREIBUNG to gegenstandstypBeschreibung,
                    InventurAppDatabaseContract.GegenstandstypErstelltEventEntry.COLUMN_GEGENSTANDSTYP_ID to gegenstandstypID
                )
            )
        }

        val random = Random(0x123456789)
        for (lagerortNr in 0..5) {
            for (gegenstandstypNr in 0..5) {
                if (random.nextBoolean()) {
                    db.insert(
                        InventurAppDatabaseContract.GegenstandEntry.TABLE_NAME,
                        null,
                        contentValuesOf(
                            InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME to "Lagerort L$lagerortNr",
                            InventurAppDatabaseContract.GegenstandEntry.COLUMN_GEGENSTANDSTYP_ID to gegenstandstypNr,
                            InventurAppDatabaseContract.GegenstandEntry.COLUMN_MENGE to random.nextInt(
                                1,
                                100
                            )
                        )
                    )
                }
            }
        }


        /*
        for (lagerortNummer in 0..5) {
            val lagerort = Lagerort(
                name = "Lagerort $lagerortNummer",
                beschreibung = "Beschreibung $lagerortNummer"
            )
            lagerortMap[lagerort.name] = lagerort
        }

        for (gegenstandstypNummer in 0..5) {
            val gegenstandstyp = Gegenstandstyp(
                name = "Gegenstandstyp $gegenstandstypNummer",
                beschreibung = "Beschreibung $gegenstandstypNummer",
                ID = freieGegenstandstypId
            )
            gegenstandstypMap[gegenstandstyp.ID] = gegenstandstyp
        }

        val rand = Random(0x123456789)
        lagerortMap.values
            .filter { rand.nextBoolean() }
            .forEach { lagerort ->
                gegenstandstypMap.values
                    .filter { rand.nextBoolean() }
                    .map { Gegenstand(it, lagerort, rand.nextInt(1, 100)) }
                    .forEach { gegenstandsMap[it.typ.ID to it.ort.name] = it }
            }

         */


    }
}