package com.example.gafitouser.user.models

import com.example.gafitouser.DestinationScreen
import com.example.gafitouser.R

enum class BottomBarItem(val icon: Int, val navDestination: DestinationScreen, val text: String) {
    HOME(R.drawable.home_icon, DestinationScreen.ShowQR, "Home"),
    LOCATION(R.drawable.location_icon, DestinationScreen.Location, "Location"),
    REPORT(R.drawable.report_icon, DestinationScreen.Report, "Report"),
    PROFILE(R.drawable.profile_icon, DestinationScreen.Profile, "Profile")
}