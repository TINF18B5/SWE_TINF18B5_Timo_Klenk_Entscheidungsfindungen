package de.dhbw.ka.inventurappprototype.aktoren.datenbank

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

        standardWerte()
    }

    private fun standardWerte() {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Nothing yet to do
    }
}