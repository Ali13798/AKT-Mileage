package us.ak_tech.aktmileagetracker

import android.app.Application

class AktMileageApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        TripsRepository.initialize(this)
    }
}