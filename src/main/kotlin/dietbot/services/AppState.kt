package dietbot.services

import dietbot.domain.states.StateList

class AppState(private val stateList: List<StateList>, initial: StateList) {
    var state: StateList = initial

    fun set(newState: StateList): AppState {
        if (!checkIfInList(newState)) throw Error("This state is not allowed!")
        state = newState

        return this
    }

    fun isState(stateToCompare: StateList): Boolean {
        return state == stateToCompare
    }

    private fun checkIfInList(newState: StateList): Boolean {
        return stateList.contains(newState)
    }
}