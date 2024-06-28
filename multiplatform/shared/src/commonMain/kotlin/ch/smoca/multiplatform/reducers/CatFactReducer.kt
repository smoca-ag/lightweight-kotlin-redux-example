package ch.smoca.multiplatform.reducers

import ch.smoca.multiplatform.AppState
import ch.smoca.redux.Action
import ch.smoca.redux.Reducer

// it is considered best practice to create separate reducers for each domain (Person, Cat, etc...)
class CatFactReducer: Reducer<AppState> {
    // define all reducer related actions
    sealed class CatFactAction: Action {
        data class Update(val fact: String): CatFactAction()
    }

    override fun reduce(action: Action, state: AppState): AppState {
        // assure only related actions are handled by this reducer
        val countAction = action as? CatFactAction ?: return state

        // apply changes to the state, depending on which action was dispatched
        return when (countAction) {
            is CatFactAction.Update -> state.copy(catFact = countAction.fact)
        }
    }
}