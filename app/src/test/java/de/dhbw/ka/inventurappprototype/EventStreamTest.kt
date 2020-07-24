package de.dhbw.ka.inventurappprototype

import de.dhbw.ka.inventurappprototype.aktoren.EventPriority
import de.dhbw.ka.inventurappprototype.aktoren.EventStream
import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortBearbeitetEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortErstelltEvent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.time.milliseconds

class EventStreamTest {

    lateinit var eventStream: EventStream

    @Before
    fun initialize() {
        eventStream = EventStream()
    }

    @Test
    fun checkThatListenerReceivesEvent() {
        var received = false
        val toSend =
            LagerortBearbeitetEvent(Date(), "nutzer", "name", "neuerName", "neueBeschreibung")


        eventStream.register<LagerortBearbeitetEvent>({
            received = true
            Assert.assertEquals(it, toSend)
        })

        eventStream.publish(toSend)
        Thread.sleep(1000)

        Assert.assertTrue("Expected this to be true", received)
    }

    @Test
    fun checkThatListenerReceivesEventOfSubtype() {
        var received = false
        val toSend =
            LagerortBearbeitetEvent(Date(), "nutzer", "name", "neuerName", "neueBeschreibung")


        eventStream.register<AbstractEvent>({
            received = true
            Assert.assertEquals(it, toSend)
        })

        eventStream.publish(toSend)
        Thread.sleep(1000)

        Assert.assertTrue("Expected this to be true", received)
    }

    @Test
    fun checkThatHigherPriorityIsExecutedFirst() {
        val list = mutableListOf<String>()
        eventStream.register<AbstractEvent>(priority = EventPriority.NORMAL, handler = {
            list.add("NORMAL")
        })

        eventStream.register<AbstractEvent>(priority = EventPriority.HIGH, handler = {
            list.add("HIGH")
        })

        eventStream.publish(LagerortErstelltEvent(
            timestamp = Date(),
            nutzername = "admin",
            name = "name",
            beschreibung = "beschreibung"
        ))

        Thread.sleep(1000)
        Assert.assertEquals("Expected both listeners to answer", 2, list.size)
        Assert.assertEquals("Expected HIGH to come first", "HIGH", list[0])
        Assert.assertEquals("Expected NORMAL to come second", "NORMAL", list[1])
    }
}