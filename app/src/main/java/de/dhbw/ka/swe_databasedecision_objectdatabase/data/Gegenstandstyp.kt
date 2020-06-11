package de.dhbw.ka.swe_databasedecision_objectdatabase.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class Gegenstandstyp(@Id var id: Long, var beschreibung: String, var name: String) {
    lateinit var gegenstaende: ToMany<Gegenstand>;
}