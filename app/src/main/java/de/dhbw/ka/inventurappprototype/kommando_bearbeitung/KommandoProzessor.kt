package de.dhbw.ka.inventurappprototype.kommando_bearbeitung

import de.dhbw.ka.inventurappprototype.daten.kommandos.AbstractKommando

/**
 * Denotiert einen Kommandoprozessor.
 */
interface KommandoProzessor<T : AbstractKommando> {
    /**
     * Bearbeite das Kommando.
     * Muss ein [KommandoErgebnis] zurückliefern.
     *
     * Return aus dieser Methode ist allerdings nicht mit dem Abschluss der Bearbeitung
     * des Kommandos gleichzusetzen, auch wenn die meisten Implementierungen so gestaltet sind.
     */
    fun bearbeite(kommando: T): KommandoErgebnis
}