package de.dhbw.ka.inventurappprototype.daten.nutzer

import java.util.*

data class Nutzer(val name: String, val passwortHash: String, val sessionToken: String)

fun authentifiziereNutzer(name: String, password: String): Nutzer? = //TODO: Implement
    if (name == password)
        Nutzer(name, password, Date().toString())
    else null