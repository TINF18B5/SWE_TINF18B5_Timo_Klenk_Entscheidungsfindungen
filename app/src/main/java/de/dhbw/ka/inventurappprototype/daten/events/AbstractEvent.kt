package de.dhbw.ka.inventurappprototype.daten.events

import java.util.*

/**
 * Denotiert ein Abstraktes Event.
 * Alle Events, die auf dem [de.dhbw.ka.inventurappprototype.aktoren.EventStream]
 * verarbeitet werden sollen, müssen diese Klasse erweitern.
 */
abstract class AbstractEvent(
    /**
     * Der Zeitpunkt des Events
     */
    val timestamp: Date,

    /**
     * Der Nutzer, der dieses Event (indirekt), beispielsweise durch ein Kommando ausgelöst hat.
     */
    val nutzername: String)