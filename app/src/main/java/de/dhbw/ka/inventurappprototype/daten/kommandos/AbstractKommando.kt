package de.dhbw.ka.inventurappprototype.daten.kommandos

/**
 * Denotiert ein Abstraktes Kommando.
 * Jedes Kommando, das von einem [de.dhbw.ka.inventurappprototype.kommando_bearbeitung.KommandoProzessor]
 * bearbeitet werden soll, muss diese Klasse erweitern.
 */
abstract class AbstractKommando(val nutzerName: String)