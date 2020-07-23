package de.dhbw.ka.inventurappprototype.aktoren

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import java.util.*

/**
 * Auf den EventStream können Events veröffentlicht werden.
 * Er ist über den globalen Aktoren-Kontext erreichbar.
 *
 * Prinzipiell kann sich jeder darauf abonnieren.
 */
class EventStream {
    val listeners: MutableMap<Class<*>?, MutableList<EventListener<AbstractEvent>>> =
        mutableMapOf()

    /**
     * Registriert einen Event listener.
     * Akzeptiert den gegebenen Typ und alle Untertypen des Events.
     *
     * Also ein EventListener auf AbstractEvent erhält alle Events die es gibt.
     */
    inline fun <reified T : AbstractEvent> register(noinline handler: EventListener<T>) {
        val list = listeners.computeIfAbsent(T::class.java) { mutableListOf() }

        list.add { event: AbstractEvent -> handler(event as T) }
    }

    fun <T: AbstractEvent> unregister(handler: EventListener<T>) {
        listeners.values.forEach {
            it.remove(handler)
        }
    }

    /**
     * Versendet ein Event an alle Event listener
     */
    fun publish(event: AbstractEvent) {
        val foundClasses: MutableSet<Class<*>> = mutableSetOf()
        val classesToCheck: Queue<Class<*>> = LinkedList()
        classesToCheck.offer(event.javaClass)
        foundClasses.add(event.javaClass)

        while (classesToCheck.isNotEmpty()) {
            val poll: Class<*> = classesToCheck.poll() ?: continue
            listeners[poll]?.forEach { it(event) }
            for (implementedInterface in poll.interfaces) {
                if (foundClasses.add(implementedInterface)) {
                    classesToCheck.offer(implementedInterface)
                }
            }
            val superClass: Class<*> = poll.superclass ?: continue
            if (foundClasses.add(superClass)) {
                classesToCheck.offer(superClass)
            }
        }
    }

    /*
    //vermutlich YAGNI, aber ich lass es mal drin
    inline fun <reified T : U, U : AbstractEvent> registerContravariant(noinline handler: (event: U) -> Unit) {
        val list = listeners.computeIfAbsent(T::class.java) { mutableListOf() }
        @Suppress("UNCHECKED_CAST")
        list.add { event: AbstractEvent -> handler(event as U) }
    }
 */
}