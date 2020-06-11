package de.dhbw.ka.swe_databasedecision_objectdatabase.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class Lager(@Id var id: Long) {
    lateinit var lagerorte: ToMany<Lagerort>;
    lateinit var gegenstandstypen: ToMany<Gegenstandstyp>;
}