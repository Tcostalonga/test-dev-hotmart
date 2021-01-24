package tarsila.costalonga.testdevhotmart.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListLocations(
    val listLocations : List<Locations>
): Parcelable

@Parcelize
data class Locations(
    val id : Int,
    val name: String,
    val review: Float,
    val type: String,
) : Parcelable

data class DetailLocation(
    val id : Int,
    val name: String,
    val review: Float,
    val type: String,
    val about: String,
    val phone: String,
    val address: String,
    val schedule: Schedule
)


data class Schedule(
    val monday: Timing,
    val tuesday: Timing,
    val wednesday: Timing,
    val thursday: Timing,
    val friday: Timing,
    val saturday: Timing,
    val sunday: Timing,
)


data class Timing(
    val open: String,
    val close: String
)