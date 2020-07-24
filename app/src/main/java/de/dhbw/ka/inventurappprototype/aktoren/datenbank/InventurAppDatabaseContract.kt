package de.dhbw.ka.inventurappprototype.aktoren.datenbank

@Suppress("MemberVisibilityCanBePrivate")
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

    class LagerortBearbeitetEventEntry {
        companion object {
            const val TABLE_NAME = "LagerortBearbeitetEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_NAME = "name"

            const val COLUMN_NEUER_NAME = "neuer_name"
            const val COLUMN_NEUE_BESCHREIBUNG = "neue_beschreibung"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_NAME TEXT NOT NULL, $COLUMN_NEUER_NAME TEXT NOT NULL, $COLUMN_NEUE_BESCHREIBUNG TEXT, PRIMARY KEY ($COLUMN_ZEITSTEMPEL, $COLUMN_NUTZER, $COLUMN_NAME))"
        }
    }

    class LagerortErstelltEventEntry {
        companion object {
            const val TABLE_NAME = "LagerortErstelltEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_NAME = "name"
            const val COLUMN_BESCHREIBUNG = "beschreibung"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_NAME TEXT NOT NULL, $COLUMN_BESCHREIBUNG TEXT, PRIMARY KEY ($COLUMN_ZEITSTEMPEL, $COLUMN_NUTZER, $COLUMN_NAME))"
        }
    }

    class GegenstandGeloschtEventEntry {
        companion object {
            const val TABLE_NAME = "GegenstandGeloschtEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_GRUND = "grund"
            const val COLUMN_LAGERORT_NAME = "lagerort_name"
            const val COLUMN_GEGENSTANDSTYP_ID = "gegenstandstyp_id"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_LAGERORT_NAME TEXT NOT NULL, $COLUMN_GEGENSTANDSTYP_ID INTEGER NOT NULL, $COLUMN_GRUND TEXT, PRIMARY KEY($COLUMN_ZEITSTEMPEL, $COLUMN_GEGENSTANDSTYP_ID, $COLUMN_NUTZER, $COLUMN_LAGERORT_NAME))"
        }
    }

    class GegenstandEingelagertEventEntry {
        companion object {
            const val TABLE_NAME = "GegenstandEingelagertEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_LAGERORT_NAME = "lagerort_name"
            const val COLUMN_GEGENSTANDSTYP_ID = "gegenstandstyp_id"
            const val COLUMN_MENGE = "menge"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_LAGERORT_NAME TEXT NOT NULL, $COLUMN_GEGENSTANDSTYP_ID INTEGER NOT NULL, $COLUMN_MENGE INTEGER NOT NULL, PRIMARY KEY($COLUMN_ZEITSTEMPEL, $COLUMN_NUTZER, $COLUMN_GEGENSTANDSTYP_ID, $COLUMN_LAGERORT_NAME))"
        }
    }

    class GegenstandAusgelagertEventEntry {
        companion object {
            const val TABLE_NAME = "GegenstandAusgelagertEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_LAGERORT_NAME = "lagerort_name"
            const val COLUMN_GEGENSTANDSTYP_ID = "gegenstandstyp_id"
            const val COLUMN_MENGE = "menge"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_LAGERORT_NAME TEXT NOT NULL, $COLUMN_GEGENSTANDSTYP_ID INTEGER NOT NULL, $COLUMN_MENGE INTEGER NOT NULL, PRIMARY KEY($COLUMN_ZEITSTEMPEL, $COLUMN_NUTZER, $COLUMN_GEGENSTANDSTYP_ID, $COLUMN_LAGERORT_NAME))"
        }
    }

    class GegenstandBearbeitetEventEntry {
        companion object {
            const val TABLE_NAME = "GegenstandBearbeitetEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_LAGERORT_NAME = "lagerort_name"
            const val COLUMN_GEGENSTANDSTYP_ID = "gegenstandstyp_id"
            const val COLUMN_NEUE_MENGE = "neue_menge"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_LAGERORT_NAME TEXT NOT NULL, $COLUMN_GEGENSTANDSTYP_ID INTEGER NOT NULL, $COLUMN_NEUE_MENGE INTEGER NOT NULL, PRIMARY KEY($COLUMN_ZEITSTEMPEL, $COLUMN_NUTZER, $COLUMN_LAGERORT_NAME, $COLUMN_GEGENSTANDSTYP_ID))"
        }
    }

    class GegenstandErstelltEventEntry {
        companion object {
            const val TABLE_NAME = "GegenstandErstelltEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_LAGERORT_NAME = "lagerort_name"
            const val COLUMN_GEGENSTANDSTYP_ID = "gegenstandstyp_id"
            const val COLUMN_MENGE = "menge"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_LAGERORT_NAME TEXT NOT NULL, $COLUMN_GEGENSTANDSTYP_ID INTEGER NOT NULL, $COLUMN_MENGE INTEGER NOT NULL, PRIMARY KEY ($COLUMN_ZEITSTEMPEL, $COLUMN_NUTZER, $COLUMN_LAGERORT_NAME, $COLUMN_GEGENSTANDSTYP_ID))"
        }
    }

    class GegenstandstypErstelltEventEntry {
        companion object {
            const val TABLE_NAME = "GegenstandstypErstelltEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_GEGENSTANDSTYP_ID = "gegenstandstyp_id"
            const val COLUMN_NAME = "name"
            const val COLUMN_BESCHREIBUNG = "beschreibung"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_GEGENSTANDSTYP_ID INTEGER NOT NULL, $COLUMN_NAME TEXT NOT NULL, $COLUMN_BESCHREIBUNG TEXT, PRIMARY KEY($COLUMN_ZEITSTEMPEL, $COLUMN_NUTZER, $COLUMN_GEGENSTANDSTYP_ID))"
        }
    }

    class GegenstandstypBearbeitetEventEntry {
        companion object {
            const val TABLE_NAME = "GegenstandstypBearbeitetEvent"
            const val COLUMN_ZEITSTEMPEL = "zeitstempel"
            const val COLUMN_NUTZER = "nutzer"
            const val COLUMN_GEGENSTANDSTYP_ID = "gegenstandstyp_id"
            const val COLUMN_NEUER_NAME = "neuer_name"
            const val COLUMN_NEUE_BESCHREIBUNG = "neue_beschreibung"

            const val CREATE_SQL =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ZEITSTEMPEL INTEGER NOT NULL, $COLUMN_NUTZER TEXT NOT NULL, $COLUMN_GEGENSTANDSTYP_ID INTEGER NOT NULL, $COLUMN_NEUER_NAME TEXT NOT NULL, $COLUMN_NEUE_BESCHREIBUNG TEXT, PRIMARY KEY($COLUMN_ZEITSTEMPEL, $COLUMN_NUTZER, $COLUMN_GEGENSTANDSTYP_ID))"
        }
    }
}