package tarsila.costalonga.testdevhotmart.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
    @SerializedName("hits") val hits: List<ImageURL> = listOf()
) : Parcelable

@Parcelize
data class ImageURL(
    @SerializedName("webformatURL") val imgURL: String =""
): Parcelable
