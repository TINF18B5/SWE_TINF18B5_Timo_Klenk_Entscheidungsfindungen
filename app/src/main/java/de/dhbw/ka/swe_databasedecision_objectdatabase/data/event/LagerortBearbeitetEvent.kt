package de.dhbw.ka.swe_databasedecision_objectdatabase.data.event

import de.dhbw.ka.swe_databasedecision_objectdatabase.data.Lagerort
import io.objectbox.annotation.Entity
import io.objectbox.relation.ToOne
import java.util.*

@Entity
class LagerortBearbeitetEvent(
    id: Long,
    zeitStempel: Date,
    var neuerName: String,
    var neueBeschreibung: String
) : Event(id, zeitStempel) {

    lateinit var lagerort: ToOne<Lagerort>

    override fun toString(): String {
        return "Lagerort ${lagerort.target.id} wurde bearbeitet zu: $neuerName und $neueBeschreibung"
    }
}