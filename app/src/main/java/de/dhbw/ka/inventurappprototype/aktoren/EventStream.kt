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
    val normalPriorityListeners: MutableMap<Class<*>, MutableList<EventListener<AbstractEvent>>> =
        mutableMapOf()

    val highPriorityListeners: MutableMap<Class<*>, MutableList<EventListener<AbstractEvent>>> =
        mutableMapOf()

    /**
     * Registriert einen Event listener.
     * Akzeptiert den gegebenen Typ und alle Untertypen des Events.
     *
     * Also ein EventListener auf AbstractEvent erhält alle Events die es gibt.
     */
    inline fun <reified T : AbstractEvent> register(
        noinline handler: EventListener<T>,
        priority: EventPriority = EventPriority.NORMAL
    ) {
        val list = when (priority) {
            EventPriority.HIGH -> highPriorityListeners
            EventPriority.NORMAL -> normalPriorityListeners
        }.computeIfAbsent(T::class.java) { mutableListOf() }

        when (priority) {
            EventPriority.HIGH -> list.add { event: AbstractEvent -> handler(event as T) }
            EventPriority.NORMAL -> list.add { event: AbstractEvent -> handler(event as T) }
        }
    }

    /**
     * Entfernt einen Listener wieder.
     * Bedeutet aber auch, dass der Abonnierende eine Referenz auf den Listener behalten muss.
     */
    fun <T : AbstractEvent> unregister(handler: EventListener<T>) {
        normalPriorityListeners.values.forEach {
            it.remove(handler)
        }

        highPriorityListeners.values.forEach {
            it.remove(handler)
        }
    }

    /**
     * Versendet ein Event an alle Event listener
     */
    fun publish(event: AbstractEvent) {
        publishOn(event, highPriorityListeners)
        publishOn(event, normalPriorityListeners)
    }

    /**
     * Versendet ein Event an alle Event listener
     */
    private fun publishOn(event: AbstractEvent, listenerMap: Map<Class<*>, MutableList<EventListener<AbstractEvent>>>) {
        val foundClasses: MutableSet<Class<*>> = mutableSetOf()
        val classesToCheck: Queue<Class<*>> = LinkedList()
        classesToCheck.offer(event.javaClass)
        foundClasses.add(event.javaClass)

        while (classesToCheck.isNotEmpty()) {
            val poll: Class<*> = classesToCheck.poll() ?: continue
            listenerMap[poll]?.forEach { it(event) }
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
}