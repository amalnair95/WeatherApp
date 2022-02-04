package com.example.weatherapp.multiDex

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

open class MultiDexApp  : Application() {
    /**
     * MultiDex must be installed in attachBaseContext() method
     * as advised here https://developer.android.com/studio/build/multidex#mdex-gradle
     * @param base
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
