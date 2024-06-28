package ch.smoca.multiplatform.reducers

import ch.smoca.multiplatform.AppState
import ch.smoca.redux.Action
import ch.smoca.redux.Reducer

// it is considered best practice to create separate reducers for each domain (Person, Cat, etc...)
class CountReducer: Reducer<AppState> {
    // define all reducer related actions
    sealed class CountAction: Action {
        data object Decrease: CountAction()
        data object Increase: CountAction()
    }

    override fun reduce(action: Action, state: AppState): AppState {
        // assure only related actions are handled by this reducer
        val countAction = action as? CountAction ?: return state

        // apply changes to the state, depending on which action was dispatched
        return when (countAction) {
            CountAction.Decrease -> state.copy(count = state.count - 1)
            CountAction.Increase -> state.copy(count = state.count + 1)
        }
    }
}