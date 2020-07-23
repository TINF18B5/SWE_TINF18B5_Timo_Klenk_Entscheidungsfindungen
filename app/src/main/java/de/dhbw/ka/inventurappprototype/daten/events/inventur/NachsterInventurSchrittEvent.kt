package de.dhbw.ka.inventurappprototype.daten.events.inventur

import de.dhbw.ka.inventurappprototype.daten.events.AbstractEvent
import de.dhbw.ka.inventurappprototype.daten.inventur.InventurSchritt
import java.util.*


class NachsterInventurSchrittEvent(
    timestamp: Date,
    nutzername: String,
    val schritt: InventurSchritt
) :
    AbstractEvent(timestamp, nutzername)
/*

, Parcelable {
constructor(parcel: Parcel) : this(
    parcel.readSerializable() as Date,
    parcel.readString()!!,
    parcel.readParcelable(InventurSchritt::class.java.classLoader)!!
)

override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeSerializable(timestamp)
    parcel.writeString(nutzername)
    parcel.writeParcelable(schritt, flags)
}

override fun describeContents(): Int {
    return 0
}

companion object CREATOR : Parcelable.Creator<NachsterInventurSchrittEvent> {
    override fun createFromParcel(parcel: Parcel): NachsterInventurSchrittEvent {
        return NachsterInventurSchrittEvent(parcel)
    }

    override fun newArray(size: Int): Array<NachsterInventurSchrittEvent?> {
        return arrayOfNulls(size)
    }


}
}

 */