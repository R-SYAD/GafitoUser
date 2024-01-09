package com.example.gafitouser.data

data class ParkirData(
    var parkirId: String? = null,
    var userId: String? = null,
    var noPolisi: String? = null,
    var merek: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var locationName: String? = null,
    var time: Long? = null,
    var isLocationMarked: Boolean? = null
)
