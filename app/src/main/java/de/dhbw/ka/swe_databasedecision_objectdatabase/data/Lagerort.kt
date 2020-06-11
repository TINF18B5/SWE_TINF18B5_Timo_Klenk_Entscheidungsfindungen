package de.dhbw.ka.swe_databasedecision_objectdatabase.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity(useNoArgConstructor = false)
class Lagerort(var name: String, var beschreibung: String, @Id var id: Long) {
    lateinit var gegenstaende: ToMany<Gegenstand>;
}