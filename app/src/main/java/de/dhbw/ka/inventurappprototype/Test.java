package de.dhbw.ka.inventurappprototype;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import de.dhbw.ka.inventurappprototype.aktoren.AktorenMain;
import de.dhbw.ka.inventurappprototype.daten.kommandos.AbstractKommando;
import de.dhbw.ka.inventurappprototype.daten.kommandos.gegenstand.GegenstandEinlagernKommando;

public class Test {
    public static void main(String[] args) {
        ActorSystem<AbstractKommando> testSystem = ActorSystem.create(Behaviors.setup(AktorenMain::new), "testSystem");
        testSystem.tell(new GegenstandEinlagernKommando("nutzer", 100, "Lager 1", 0x815));
    }
}
