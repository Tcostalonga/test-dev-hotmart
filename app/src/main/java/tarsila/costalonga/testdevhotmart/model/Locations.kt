package tarsila.costalonga.testdevhotmart.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListLocations(
    @SerializedName("listLocations") val listLocations: List<Locations>
) : Parcelable

@Parcelize
data class Locations(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("review") val review: Float,
    @SerializedName("type") val type: String,
) : Parcelable

data class DetailLocation(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("review") val review: Float,
    @SerializedName("type") val type: String,
    @SerializedName("about") val about: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("adress") val address: String,
    @SerializedName("schedule") val schedule: Schedule
)

data class Schedule(
    @SerializedName("monday") val monday: Timing,
    @SerializedName("tuesday") val tuesday: Timing,
    @SerializedName("wednesday") val wednesday: Timing,
    @SerializedName("thursday") val thursday: Timing,
    @SerializedName("friday") val friday: Timing,
    @SerializedName("saturday") val saturday: Timing,
    @SerializedName("sunday") val sunday: Timing,
)

data class Timing(
    @SerializedName("open") val open: String,
    @SerializedName("close") val close: String
)