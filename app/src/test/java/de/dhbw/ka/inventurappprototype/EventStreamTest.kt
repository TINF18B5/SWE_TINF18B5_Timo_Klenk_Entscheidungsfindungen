package de.dhbw.ka.inventurappprototype

import de.dhbw.ka.inventurappprototype.aktoren.EventStream
import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import de.dhbw.ka.inventurappprototype.daten.events.lagerort.LagerortBearbeitetEvent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

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


        eventStream.register<LagerortBearbeitetEvent> { event ->
            received = true
            Assert.assertEquals(event, toSend)
        }

        eventStream.publish(toSend)
        Thread.sleep(1000)

        Assert.assertTrue("Expected this to be true", received)
    }

    @Test
    fun checkThatListenerReceivesEventOfSubtype() {
        var received = false
        val toSend =
            LagerortBearbeitetEvent(Date(), "nutzer", "name", "neuerName", "neueBeschreibung")


        eventStream.register<AbstractEvent> { event ->
            received = true
            Assert.assertEquals(event, toSend)
        }

        eventStream.publish(toSend)
        Thread.sleep(1000)

        Assert.assertTrue("Expected this to be true", received)
    }
}