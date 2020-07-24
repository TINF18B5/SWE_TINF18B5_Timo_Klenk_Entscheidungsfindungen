package de.dhbw.ka.inventurappprototype.aktoren.datenbank

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import kotlin.random.Random

const val filename = "InventurApp.db"
const val version = 1

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

        standardWerte(db)
    }

    private fun standardWerte(db: SQLiteDatabase) {

        for (i in 0..5) {
            db.insert(
                InventurAppDatabaseContract.LagerortEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.LagerortEntry.COLUMN_NAME to "Lagerort L$i",
                    InventurAppDatabaseContract.LagerortEntry.COLUMN_BESCHREIBUNG to "Beschreibung L$i"
                )
            )
        }

        for (i in 0..5) {
            db.insert(
                InventurAppDatabaseContract.GegenstandstypEntry.TABLE_NAME,
                null,
                contentValuesOf(
                    InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_ID to i,
                    InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_NAME to "Gegenstandstyp G$i",
                    InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_BESCHREIBUNG to "Beschreibung G$i"
                )
            )
        }

        val random = Random(0x123456789)
        for (lagerortNr in 0..5) {
            for (gegenstandstypNr in 0..5) {
                if(random.nextBoolean()) {
                    db.insert(
                        InventurAppDatabaseContract.GegenstandEntry.TABLE_NAME,
                        null,
                        contentValuesOf(
                            InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME to "Lagerort L$lagerortNr",
                            InventurAppDatabaseContract.GegenstandEntry.COLUMN_GEGENSTANDSTYP_ID to gegenstandstypNr,
                            InventurAppDatabaseContract.GegenstandEntry.COLUMN_MENGE to random.nextInt(1, 100)
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

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Nothing yet to do
    }
}