package hu.hm.ibm_jetcompose.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Item(
    val guid: String = "",
    val email: String = "",
    val userName: String = "",
    val description: String = "",
    val title: String = "",
    val avatarURL: String = "",
    val durationInSec: Long = 0,
    val mediaType: String = "",
    val created: String = ""
): Parcelable