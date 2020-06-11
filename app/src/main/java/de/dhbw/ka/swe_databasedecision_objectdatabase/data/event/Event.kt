package de.dhbw.ka.swe_databasedecision_objectdatabase.data.event

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
abstract class Event(@Id var id: Long, var zeitStempel: Date)