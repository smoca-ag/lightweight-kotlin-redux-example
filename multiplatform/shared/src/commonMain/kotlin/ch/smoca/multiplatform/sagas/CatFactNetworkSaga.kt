package ch.smoca.multiplatform.sagas

import ch.smoca.multiplatform.AppState
import ch.smoca.multiplatform.network.CatFact
import ch.smoca.multiplatform.reducers.CatFactReducer
import ch.smoca.redux.Action
import ch.smoca.redux.Saga
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

// it is considered best practice to create separate sagas for each domain (Network, Backup, etc...)
class CatFactNetworkSaga : Saga<AppState>() {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    private val url: String = "https://catfact.ninja/fact"

    sealed class NetworkAction : Action {
        data object FetchCatFact : NetworkAction()
    }

    // this method runs on IO thread with limited parallelism = 1
    // This means that onAction will be called for each action one by one,
    // and even blocking operations in onAction will not block other sagas
    // or the flow of the action to the reducers
    override suspend fun onAction(action: Action, oldState: AppState, newState: AppState) {
        val networkAction = action as? NetworkAction ?: return

        when (networkAction) {
            is NetworkAction.FetchCatFact -> getCatFact()
        }
    }

    private suspend fun getCatFact() {
        val response = client.get(url)
        if (response.status.value in 200..299) {
            val fact: CatFact = response.body()
            dispatch(CatFactReducer.CatFactAction.Update(fact.fact))
        }
    }
}