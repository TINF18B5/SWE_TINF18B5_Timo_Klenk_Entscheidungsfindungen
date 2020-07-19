package de.dhbw.ka.inventurappprototype.aktoren

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.javadsl.AbstractBehavior
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Behaviors
import akka.actor.typed.javadsl.Receive
import de.dhbw.ka.inventurappprototype.daten.kommandos.AbstractKommando
import de.dhbw.ka.inventurappprototype.kommando_bearbeitung.ZentralerKommandoProzessor

class AktorenMain(context: ActorContext<AbstractKommando>?) :
    AbstractBehavior<AbstractKommando>(context) {


    private val kommandoProzessor: ActorRef<AbstractKommando>
            by lazy {
                context?.spawn(Behaviors.setup<AbstractKommando> {
                    ZentralerKommandoProzessor(
                        it
                    )
                }, "kommandoProzessor") ?: error("Konnte KommandoProzessor nicht spawnen!")
            }



    override fun createReceive(): Receive<AbstractKommando> = newReceiveBuilder()
        .onMessage(AbstractKommando::class.java, this::onMessage)
        .build()

    private fun onMessage(kommando: AbstractKommando): Behavior<AbstractKommando> {
        kommandoProzessor.tell(kommando)
        return Behaviors.same()
    }


}

fun createAktorenMain(): Behavior<AbstractKommando> {
    return Behaviors.setup { AktorenMain(it) }
}