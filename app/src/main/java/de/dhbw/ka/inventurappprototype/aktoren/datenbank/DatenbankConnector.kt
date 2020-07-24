package de.dhbw.ka.inventurappprototype.aktoren.datenbank

import android.content.Context
import androidx.core.content.contentValuesOf
import androidx.core.database.sqlite.transaction
import de.dhbw.ka.inventurappprototype.aktoren.AktorenKontext
import de.dhbw.ka.inventurappprototype.aktoren.EventListener
import de.dhbw.ka.inventurappprototype.aktoren.EventPriority
import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandErstelltEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstand.GegenstandGeloeschtEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp.GegenstandstypBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.gegenstandstyp.GegenstandstypErstelltEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortErstelltEvent
import de.dhbw.ka.inventurappprototype.daten.gegenstand.Gegenstand
import de.dhbw.ka.inventurappprototype.daten.gegenstandstyp.Gegenstandstyp
import de.dhbw.ka.inventurappprototype.daten.lagerort.Lagerort
import de.dhbw.ka.inventurappprototype.gui.gegenstand.liste.GegenstandsListenArt

class DatenbankConnector(context: Context) {
    private val gegenstandsMap: MutableMap<Pair<Int, String>, Gegenstand> = mutableMapOf()
    private val gegenstandstypMap: MutableMap<Int, Gegenstandstyp> = mutableMapOf()
    private val lagerortMap: MutableMap<String, Lagerort> = mutableMapOf()

    private val openHelper: InventurAppOpenHelper = InventurAppOpenHelper(context)

    init {
        liesDatenbankEin()
    }

    /**
     * Liest die Datenbank in den internen Speicher.
     * Kann bei späterer Implementierung mit direktem DB-Zugriff entfernt werden.
     */
    private fun liesDatenbankEin() {
        openHelper.readableDatabase.query(
            true,
            InventurAppDatabaseContract.GegenstandstypEntry.TABLE_NAME,
            arrayOf(
                InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_ID,
                InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_NAME,
                InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_BESCHREIBUNG
            ), null, null, null, null, null, null, null
        ).use {
            val indexID =
                it.getColumnIndex(InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_ID)
            val indexName =
                it.getColumnIndex(InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_NAME)
            val indexBeschreibung =
                it.getColumnIndex(InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_BESCHREIBUNG)
            while (it.moveToNext()) {
                val gegenstandstyp = Gegenstandstyp(
                    ID = it.getInt(indexID),
                    name = it.getString(indexName),
                    beschreibung = it.getString(indexBeschreibung)
                )
                gegenstandstypMap[gegenstandstyp.ID] = gegenstandstyp
            }
        }

        openHelper.readableDatabase.query(
            true,
            InventurAppDatabaseContract.LagerortEntry.TABLE_NAME,
            arrayOf(
                InventurAppDatabaseContract.LagerortEntry.COLUMN_NAME,
                InventurAppDatabaseContract.LagerortEntry.COLUMN_BESCHREIBUNG
            ), null, null, null, null, null, null, null
        ).use {
            val indexName = it.getColumnIndex(InventurAppDatabaseContract.LagerortEntry.COLUMN_NAME)
            val indexBeschreibung =
                it.getColumnIndex(InventurAppDatabaseContract.LagerortEntry.COLUMN_BESCHREIBUNG)
            while (it.moveToNext()) {
                val lagerort = Lagerort(
                    name = it.getString(indexName),
                    beschreibung = it.getString(indexBeschreibung)
                )
                lagerortMap[lagerort.name] = lagerort
            }
        }


        openHelper.readableDatabase.query(
            true,
            InventurAppDatabaseContract.GegenstandEntry.TABLE_NAME,
            arrayOf(
                InventurAppDatabaseContract.GegenstandEntry.COLUMN_GEGENSTANDSTYP_ID,
                InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME,
                InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME
            ),
            null, null, null, null, null, null
        ).use {
            val indexID =
                it.getColumnIndex(InventurAppDatabaseContract.GegenstandEntry.COLUMN_GEGENSTANDSTYP_ID)
            val indexLagerort =
                it.getColumnIndex(InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME)
            val indexMenge =
                it.getColumnIndex(InventurAppDatabaseContract.GegenstandEntry.COLUMN_MENGE)
            while (it.moveToNext()) {
                Gegenstand(
                    typ = gegenstandstyp(it.getInt(indexID))!!,
                    ort = lagerort(it.getString(indexLagerort))!!,
                    menge = it.getInt(indexMenge)
                )
            }
        }
    }


    private val eventListener: EventListener<AbstractEvent> = { event ->
        when (event) {
            is GegenstandBearbeitetEvent -> {
                val copy =
                    gegenstandsMap[event.gegenstandstypID to event.lagerortName]!!.copy(menge = event.menge)
                gegenstandsMap[event.gegenstandstypID to event.lagerortName] = copy
                openHelper.writableDatabase.update(
                    InventurAppDatabaseContract.GegenstandEntry.TABLE_NAME,
                    contentValuesOf(
                        InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME to event.lagerortName,
                        InventurAppDatabaseContract.GegenstandEntry.COLUMN_GEGENSTANDSTYP_ID to event.gegenstandstypID,
                        InventurAppDatabaseContract.GegenstandEntry.COLUMN_MENGE to event.menge
                    ),
                    "${InventurAppDatabaseContract.GegenstandEntry.COLUMN_GEGENSTANDSTYP_ID} = ? AND ${InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME} = ?",
                    arrayOf(event.lagerortName, event.gegenstandstypID.toString())
                )
            }
            is GegenstandErstelltEvent -> {
                val gegenstand = Gegenstand(
                    gegenstandstyp(event.gegenstandstypID)!!,
                    lagerort(event.lagerortName)!!,
                    event.menge
                )
                gegenstandsMap[event.gegenstandstypID to event.lagerortName] = gegenstand
                openHelper.writableDatabase.insert(
                    InventurAppDatabaseContract.GegenstandEntry.TABLE_NAME,
                    null,
                    contentValuesOf(
                        InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME to event.lagerortName,
                        InventurAppDatabaseContract.GegenstandEntry.COLUMN_GEGENSTANDSTYP_ID to event.gegenstandstypID,
                        InventurAppDatabaseContract.GegenstandEntry.COLUMN_MENGE to event.menge
                    )
                )
            }
            is GegenstandGeloeschtEvent -> {
                gegenstandsMap.remove(event.gegenstandstypID to event.lagerortName)
                openHelper.writableDatabase.delete(
                    InventurAppDatabaseContract.GegenstandEntry.TABLE_NAME,
                    "${InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME} = ? AND ${InventurAppDatabaseContract.GegenstandEntry.COLUMN_GEGENSTANDSTYP_ID} = ?",
                    arrayOf(event.lagerortName, event.gegenstandstypID.toString())
                )
            }
            is GegenstandstypErstelltEvent -> {
                val gegenstandstyp = Gegenstandstyp(event.name, event.beschreibung, event.ID)
                gegenstandstypMap[event.ID] = gegenstandstyp
                openHelper.writableDatabase.insert(
                    InventurAppDatabaseContract.GegenstandstypEntry.TABLE_NAME,
                    null,
                    contentValuesOf(
                        InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_ID to event.ID,
                        InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_NAME to event.name,
                        InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_BESCHREIBUNG to event.beschreibung
                    )
                )
            }
            is GegenstandstypBearbeitetEvent -> {
                val copy = gegenstandstypMap[event.ID]!!.copy(
                    name = event.neuerName,
                    beschreibung = event.neueBeschreibung
                )
                gegenstandstypMap[event.ID] = copy
                openHelper.writableDatabase.update(
                    InventurAppDatabaseContract.GegenstandstypEntry.TABLE_NAME,
                    contentValuesOf(
                        InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_ID to copy.ID,
                        InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_NAME to copy.name,
                        InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_BESCHREIBUNG to copy.beschreibung
                    ),
                    "${InventurAppDatabaseContract.GegenstandstypEntry.COLUMN_ID} = ?",
                    arrayOf(event.ID.toString())
                )
            }
            is LagerortErstelltEvent -> {
                val lagerort = Lagerort(event.name, event.beschreibung)
                lagerortMap[event.name] = lagerort
                openHelper.writableDatabase.insert(
                    InventurAppDatabaseContract.LagerortEntry.TABLE_NAME,
                    null,
                    contentValuesOf(
                        InventurAppDatabaseContract.LagerortEntry.COLUMN_NAME to lagerort.name,
                        InventurAppDatabaseContract.LagerortEntry.COLUMN_BESCHREIBUNG to lagerort.beschreibung
                    )
                )
            }
            is LagerortBearbeitetEvent -> {
                val copy = lagerortMap[event.name]!!.copy(
                    name = event.neuerName,
                    beschreibung = event.neueBeschreibung
                )
                lagerortMap[event.neuerName] = copy

                gegenstandsMap.keys.toList()
                    .filter { it.second == event.name }
                    .forEach {
                        gegenstandsMap[it.first to event.neuerName] =
                            gegenstandsMap.remove(it)!!
                    }


                openHelper.writableDatabase.transaction {
                    update(
                        InventurAppDatabaseContract.LagerortEntry.TABLE_NAME,
                        contentValuesOf(
                            InventurAppDatabaseContract.LagerortEntry.COLUMN_NAME to copy.name,
                            InventurAppDatabaseContract.LagerortEntry.COLUMN_BESCHREIBUNG to copy.beschreibung
                        ),
                        "${InventurAppDatabaseContract.LagerortEntry.COLUMN_NAME} = ?",
                        arrayOf(event.name)
                    )

                    update(
                        InventurAppDatabaseContract.GegenstandEntry.TABLE_NAME,
                        contentValuesOf(
                            InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME to event.neuerName
                        ),
                        "${InventurAppDatabaseContract.GegenstandEntry.COLUMN_LAGERORT_NAME} = ?",
                        arrayOf(event.name)
                    )
                }
            }

        }
    }

    init {
        AktorenKontext.eventStream.register(
            eventListener,
            EventPriority.HIGH
        )

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

    fun onDestroy() {
        AktorenKontext.eventStream.unregister(eventListener)



        openHelper.close()
    }

    fun gegenstaende(listenart: GegenstandsListenArt): List<Gegenstand> {
        return when (listenart) {
            is GegenstandsListenArt.LagerortVerwaltung -> gegenstaende(listenart.lagerort)
            is GegenstandsListenArt.GegenstandstypVerwaltung -> gegenstaende(listenart.gegenstandstyp)
        }
    }

    fun gegenstaende(lagerort: Lagerort): List<Gegenstand> =
        gegenstandsMap.values.filter { it.ort == lagerort }

    fun gegenstaende(gegenstandstyp: Gegenstandstyp): List<Gegenstand> =
        gegenstandsMap.values.filter { it.typ.ID == gegenstandstyp.ID }

    fun gegenstandstyp(gegenstandstypID: Int): Gegenstandstyp? =
        gegenstandstypMap[gegenstandstypID]

    fun lagerort(name: String): Lagerort? =
        lagerortMap[name]

    fun gegenstand(typID: Int, lagerortName: String): Gegenstand? =
        when (val lagerort = lagerort(lagerortName)) {
            null -> null
            else -> gegenstaende(lagerort).firstOrNull {
                it.typ.ID == typID
            }
        }


    val lagerorte: Collection<Lagerort>
        get() = lagerortMap.values

    val gegenstandsTypen: Collection<Gegenstandstyp>
        get() = gegenstandstypMap.values

    inline val freieGegenstandstypId: Int
        get() = (gegenstandsTypen.map { it.ID }.max() ?: 0) + 1
}