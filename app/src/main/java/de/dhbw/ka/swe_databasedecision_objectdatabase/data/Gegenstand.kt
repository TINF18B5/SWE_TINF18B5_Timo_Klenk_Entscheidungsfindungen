package de.dhbw.ka.swe_databasedecision_objectdatabase.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Gegenstand(@Id var id: Long = 0) {
    lateinit var gegenstandstyp: ToOne<Gegenstandstyp>
    lateinit var lagerort: ToOne<Lagerort>
}