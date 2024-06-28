package ch.smoca.multiplatform

import ch.smoca.redux.State
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
