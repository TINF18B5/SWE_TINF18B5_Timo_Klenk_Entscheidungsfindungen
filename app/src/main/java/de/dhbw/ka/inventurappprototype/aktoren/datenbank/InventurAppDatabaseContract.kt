package de.dhbw.ka.inventurappprototype.aktoren.datenbank

class InventurAppDatabaseContract {
    class LagerortEntry {
        companion object {
            const val TABLE_NAME = "Lagerort"
            const val COLUMN_NAME = "name"
            const val COLUMN_BESCHREIBUNG = "beschreibung"
            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_NAME TEXT PRIMARY KEY, $COLUMN_BESCHREIBUNG TEXT)"
        }
    }

    class GegenstandEntry {
        companion object {
            const val TABLE_NAME = "Gegenstand"
            const val COLUMN_LAGERORT_NAME = "lagerort_name"
            const val COLUMN_GEGENSTANDSTYP_ID = "gegenstandstyp_id"
            const val COLUMN_MENGE = "menge"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_LAGERORT_NAME TEXT NOT NULL, $COLUMN_GEGENSTANDSTYP_ID INTEGER NOT NULL, $COLUMN_MENGE INTEGER NOT NULL, PRIMARY KEY($COLUMN_GEGENSTANDSTYP_ID, $COLUMN_LAGERORT_NAME))"
        }
    }

    class GegenstandstypEntry {
        companion object {
            const val TABLE_NAME = "Gegenstandstyp"
            const val COLUMN_ID = "id"
            const val COLUMN_BESCHREIBUNG = "beschreibung"
            const val COLUMN_NAME = "name"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT NOT NULL, $COLUMN_BESCHREIBUNG TEXT)"
        }
    }

    class NutzerEntry {
        companion object {
            const val TABLE_NAME = "Nutzer"
            const val COLUMN_NAME = "name"
            const val COLUMN_PASSWORD_HASH = "password_hash"
            const val COLUMN_SESSION_TOKEN = "session_token"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_NAME TEXT PRIMARY KEY, $COLUMN_PASSWORD_HASH TEXT NOT NULL, $COLUMN_SESSION_TOKEN)"
        }
    }
}