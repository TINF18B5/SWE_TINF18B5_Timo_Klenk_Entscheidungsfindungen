package de.dhbw.ka.inventurappprototype.kommando_bearbeitung

/**
 * Gibt an, ob das Kommando bearbeitet wird, oder nicht.
 * Der Sender des Kommandos ist dafür zuständig, mit dem Ergebnis umzugehen.
 */
sealed class KommandoErgebnis {
    /**
     * Das Kommando wurde Akzeptiert.
     * Bedeutet <b>nicht</c>, dass die Bearbeitung bereits angefangen hat, oder fertig ist.
     */
    object Akzeptiert : KommandoErgebnis()

    /**
     * Das Kommando wurde nicht akzeptiert.
     * Eine Fehlermeldung kann dem Nutzer angezeigt werden.
     */
    data class NichtAkzeptiert(val fehler: String): KommandoErgebnis()
}