package ch.smoca.multiplatform

import ch.smoca.multiplatform.reducers.CatFactReducer
import ch.smoca.multiplatform.reducers.CountReducer
import ch.smoca.multiplatform.sagas.CatFactNetworkSaga
import ch.smoca.redux.State
import ch.smoca.redux.Store
import kotlinx.serialization.Serializable

// the state describes the data of the whole system
// for big states it is considered best practise to
// nest data classes for each domain (Person, Cat, etc...)
// MUST NOT BE MUTABLE
@Serializable // <- we may want to safe the state as json for app persistence/backup
data class AppState(
    val count: Int = 0,
    val catFact: String = ""
): State

// top level function to setup the store with AppState
fun setupStore(initialState: AppState = AppState()): Store<AppState> =
    Store(
        initialState = initialState,
        sagas = listOf(CatFactNetworkSaga()),
        reducers = listOf(
            CatFactReducer(),
            CountReducer()
        )
    )
