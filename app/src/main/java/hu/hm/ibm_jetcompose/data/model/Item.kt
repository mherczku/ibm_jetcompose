package hu.hm.ibm_jetcompose.data.model

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
)