package ch.smoca.multiplatform

import ch.smoca.multiplatform.reducers.CatFactReducer
import ch.smoca.multiplatform.reducers.CountReducer
import ch.smoca.multiplatform.sagas.CatFactNetworkSaga
import ch.smoca.redux.Store

class AppStore {
    companion object {
        // create a setup function which initialized the store with all sagas and reducers
        // this function must be called on the native site
        fun setup(initialState: AppState = AppState()): Store<AppState> =
            Store(
                initialState = initialState,
                sagas = listOf(CatFactNetworkSaga()),
                reducers = listOf(
                    CatFactReducer(),
                    CountReducer()
                )
            )
    }
}
