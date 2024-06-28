package ch.smoca.android

import android.app.Application
import ch.smoca.multiplatform.AppState
import ch.smoca.multiplatform.setupStore
import ch.smoca.redux.Store

class MainApplication: Application() {
    lateinit var store: Store<AppState>

    override fun onCreate() {
        super.onCreate()
        store = setupStore()
    }
}