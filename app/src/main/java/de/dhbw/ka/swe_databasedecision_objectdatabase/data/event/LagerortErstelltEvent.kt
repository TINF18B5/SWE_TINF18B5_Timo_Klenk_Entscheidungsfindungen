package de.dhbw.ka.swe_databasedecision_objectdatabase.data.event

import io.objectbox.annotation.Entity
import java.util.*

@Entity
class LagerortErstelltEvent(
    id: Long,
    zeitStempel: Date,
    var name: String,
    var beschreibung: String
) : Event(id, zeitStempel) {
    override fun toString(): String {
        return "Lagerort wurde erstellt mit: $name und $beschreibung"
    }
}